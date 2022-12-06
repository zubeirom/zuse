module com.htwsaar.zuse {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.google.gson;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.persistence;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires mysql.connector.java;
    requires java.xml.bind;
    requires org.mapstruct.processor;
    requires java.naming;
    requires java.xml;
    requires javafx.graphics;


    opens com.htwsaar.zuse to javafx.fxml;
    exports com.htwsaar.zuse;
    exports com.htwsaar.zuse.controller;
    opens com.htwsaar.zuse.controller to javafx.fxml;
    opens com.htwsaar.zuse.model to org.hibernate.orm.core, com.google.gson;
    exports com.htwsaar.zuse.model;
}