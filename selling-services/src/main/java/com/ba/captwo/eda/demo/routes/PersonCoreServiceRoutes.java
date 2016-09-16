package com.ba.captwo.eda.demo.routes;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by u760245 on 05/07/2014.
 */
public class PersonCoreServiceRoutes extends RouteBuilder {

    private final Logger log = LoggerFactory.getLogger(PersonCoreServiceRoutes.class);

    private String uri_scheme = "direct-vm";
    private String uri_base = "selling.services";
    private String resource = "person";

    private String create_uri = uri_scheme+":"+uri_base+"."+resource+".create";
    private String read_uri =   uri_scheme+":"+uri_base+"."+resource+".read";
    private String update_uri = uri_scheme+":"+uri_base+"."+resource+".update";
    private String delete_uri = uri_scheme+":"+uri_base+"."+resource+".delete";
    private String list_uri =   uri_scheme+":"+uri_base+"."+resource+".list";

    @Override
    public void configure() throws Exception {

        from(create_uri).startupOrder(1)                                                    .routeId(create_uri)
                .to("log:input")                                                            .id(create_uri + " : Log input")
                .beanRef("PersonCoreServiceBean", "createPerson(${body})")                  .id(create_uri+" : execute")
                .to("log:output")                                                           .id(create_uri+" : Log input");

        from(read_uri).startupOrder(2)                                                      .routeId(read_uri)
                .to("log:input")                                                            .id(read_uri+" : Log input")
                .beanRef("PersonCoreServiceBean", "readPerson(${in.header.pid})")           .id(read_uri+" : execute")
                .to("log:output")                                                           .id(read_uri+" : Log output");

        from(update_uri).startupOrder(3)                                                    .routeId(update_uri)
                .to("log:input")                                                            .id(update_uri + " : Log input")
                .beanRef("PersonCoreServiceBean", "updatePerson(${body})")                  .id(update_uri+" : execute")
                .to("log:output")                                                           .id(update_uri+" : Log output");

        from(delete_uri).startupOrder(4)                                                    .routeId(delete_uri)
                .to("log:input")                                                            .id(delete_uri+" : Log input")
                .beanRef("PersonCoreServiceBean", "deletePerson(${in.header.pid})")         .id(delete_uri+" : execute")
                .to("log:output")                                                           .id(delete_uri+" : Log output");

        from(list_uri).startupOrder(5)                                                      .routeId(list_uri)
                .to("log:input")                                                            .id(list_uri+" : Log input")
                .beanRef("PersonCoreServiceBean", "listPersons()")                          .id(list_uri+" : execute")
                .to("log:output")                                                           .id(list_uri+" : Log output");

    }


}