#!/bin/bash
docker pull pseudomuto/protoc-gen-doc
docker run --rm \
  -v $(pwd)/docs:/out \
  -v $(pwd)/src/main/proto:/protos \
  pseudomuto/protoc-gen-doc --doc_opt=markdown,docs.md