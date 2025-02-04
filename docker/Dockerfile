FROM symphony-docker-local.artifactory.eng.vmware.com/vmware/tango-base-openjdk-17:1.194.0-31164a9-amd64

ENV SERVICE_HOME=/opt/service

COPY files/hub-app-management-spring-demo-apps-*.jar $SERVICE_HOME/hub-app-management-spring-demo-apps.jar

COPY start.sh $SERVICE_HOME/start.sh

RUN chmod a+x $SERVICE_HOME/start.sh


WORKDIR $SERVICE_HOME
RUN ["chmod", "a+x", "./start.sh"]
ENTRYPOINT ["./start.sh"]