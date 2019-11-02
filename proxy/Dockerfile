FROM envoyproxy/envoy:latest
COPY ./envoy.yaml /etc/envoy/envoy.yaml
COPY ./descriptor_set.desc /etc/envoy/descriptor_set.desc
EXPOSE 8081
CMD /usr/local/bin/envoy -c /etc/envoy/envoy.yaml
