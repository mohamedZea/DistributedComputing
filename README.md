
# Traders & Brokers

## TCP Message in JSON
- Type (Bids, Asks)
- Code (AAPL,IBM,MSFT,ORCL)
- Amount
- Price
- Owner

## Processing
### Client
Sends Asks and Bids of different types
### Server
Dispatch actions between clients regarding asks & bids


## Requirement

- gson (2.8.2) : https://github.com/google/gson
- Apache-xmlrpc (3.1.3)
- Apache Active MQ 5.15.3 : http://activemq.apache.org/

## Explore the project

Starting the server (broker) : Server/src/TCPServer

Starting the publicher : Publisher/Publisher

---
Starting the client (trader) : Client/client/TPCClient

Starting the price client : Client/client/PriceClient

Starting the price history client : Client/client/PriceHistoryClient
	-> Enter the Stock code 
	-> Enter the date and hour of the beginning of the history
	-> Enter the date and hour of the end of the history
	-> It will show a graph but only the first time

Starting the client (improve trader) : Client/client/ImprovedTraders
