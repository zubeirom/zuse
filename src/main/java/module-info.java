module com.htwsaar.zuse {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.google.gson;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires org.mapstruct.processor;
    requires java.naming;
    requires java.xml;
    requires javafx.graphics;
    requires javax.persistence;
    requires jakarta.persistence;


    opens com.htwsaar.zuse to javafx.fxml;
    exports com.htwsaar.zuse;
    exports com.htwsaar.zuse.controller;
    opens com.htwsaar.zuse.controller to javafx.fxml;
    opens com.htwsaar.zuse.model to org.hibernate.orm.core, com.google.gson;
    exports com.htwsaar.zuse.model;
}