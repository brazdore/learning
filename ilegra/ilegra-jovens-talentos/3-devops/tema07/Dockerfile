FROM golang:latest AS builder
WORKDIR /app
COPY /main .
RUN go mod init example.com/calc && go mod tidy
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o go-calc .

FROM alpine:latest
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=builder /app/go-calc ./
EXPOSE 8080
ENTRYPOINT ["./go-calc"]