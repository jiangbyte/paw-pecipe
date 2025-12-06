# Dockerfile
FROM rabbitmq:management

COPY rabbitmq-plugins/rabbitmq_delayed_message_exchange-4.2.0.ez /opt/rabbitmq/plugins/

# 启用插件（构建时启用，安全可靠）
# RUN rabbitmq-plugins enable --offline rabbitmq_delayed_message_exchange

RUN rabbitmq-plugins enable --offline rabbitmq_management rabbitmq_delayed_message_exchange

# docker build -t my-rabbitmq-delay .