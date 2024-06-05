import asyncio
import logging
import time
from typing import AsyncGenerator, Tuple

from reactivex import operators
from rsocket.frame_helpers import ensure_bytes
from rsocket.payload import Payload
from rsocket.reactivex.back_pressure_publisher import from_observable_with_backpressure, observable_from_async_generator
from rsocket.reactivex.reactivex_handler_adapter import reactivex_handler_factory
from rsocket.routing.request_router import RequestRouter
from rsocket.routing.routing_request_handler import RoutingRequestHandler
from rsocket.rsocket_server import RSocketServer
from rsocket.streams.stream_from_async_generator import StreamFromAsyncGenerator
from rsocket.transports.tcp import TransportTCP

import petmessage_pb2 as pet_message

router = RequestRouter()


def createResponse(name: str, status: pet_message.ResponseStatus):
    response = pet_message.PetRegistrationResponse()
    response.name = name;
    response.status = status;
    return pet_message.PetRegistrationResponse.SerializeToString(response)


def pet_registration_stream(petmessage: pet_message.PetRegistrationRequest):
    async def generator() -> AsyncGenerator[Tuple[Payload, bool], None]:
        state: pet_message.ResponseStatus = pet_message.ResponseStatus.Value("RECEIVED")
        logging.info('--1 ---- Received registration request for %s', petmessage.name)
        while True:
            if state == pet_message.ResponseStatus.Value("RECEIVED"):
                state = pet_message.ResponseStatus.Value("PROCESSING")
                logging.info('--2 ---- Registration received... starting process for %s', petmessage.name)
                yield Payload(createResponse(petmessage.name, pet_message.ResponseStatus.Value("RECEIVED"))), False

            if state == pet_message.ResponseStatus.Value("PROCESSING"):
                if petmessage.name == 'AngryCat':
                    time.sleep(5)
                logging.info('--3 ---- Processing started... for %s', petmessage.name)

                state = pet_message.ResponseStatus.Value("REGISTERED")
                yield Payload(
                    createResponse(petmessage.name, pet_message.ResponseStatus.Value("PROCESSING"))), False
            logging.info('--4 ----- Processing finished... for %s', petmessage.name)
            yield Payload(createResponse(petmessage.name, state)), True
            break

    return StreamFromAsyncGenerator(generator)



@router.stream('pet.registration')
async def channel_response(payload: Payload, composite_metadata):
    decodedPayload = payload.data.decode('utf-8')
    pet_request = pet_message.PetRegistrationRequest()
    pet_request.ParseFromString(bytes(decodedPayload, 'utf-8'))
    stream = pet_registration_stream(pet_request)
    return stream


def handler_factory():
    return RoutingRequestHandler(router)


def handle_client(reader, writer):
    RSocketServer(TransportTCP(reader, writer), handler_factory=handler_factory)


def handle_rx_client(reader, writer):
    RSocketServer(TransportTCP(reader, writer), handler_factory=reactivex_handler_factory(handler_factory))


async def start_server():
    logging.basicConfig(level=logging.DEBUG)

    logging.info(f'Starting tcp server at localhost:6565')

    server = await asyncio.start_server(handle_client, 'localhost', 6565)

    async with server:
        await server.serve_forever()


if __name__ == '__main__':
    asyncio.run(start_server())
