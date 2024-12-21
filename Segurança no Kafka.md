# Segurança no Kafka

## Gerar Keystore no Formato PKCS12

> keytool -keystore {keystorefile} -alias localhost -validity {validity} -genkey -keyalg RSA -storetype pkcs12

**Exemplo de uso:**
```sh
keytool -keystore mykeystore.p12 -alias localhost -validity 365 -genkey -keyalg RSA -storetype pkcs12
```

## Gerar Keystore no Formato JKS e Converter para PKCS12

> keytool -keystore server.keystore.jks -alias localhost -validity {validity} -genkey -keyalg RSA -destkeystoretype pkcs12

**Exemplo de uso:**
```sh
keytool -keystore server.keystore.jks -alias localhost -validity 365 -genkey -keyalg RSA -destkeystoretype pkcs12
```





