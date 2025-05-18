module org.example.delta_pdv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens org.example.delta_pdv to javafx.fxml;
    exports org.example.delta_pdv;
    opens org.example.delta_pdv.entities to javafx.base;
    exports org.example.delta_pdv.entities;
    exports org.example.delta_pdv.gui.controllers;
    opens org.example.delta_pdv.gui.controllers to javafx.fxml;
    exports org.example.delta_pdv.gui.utils;
    opens org.example.delta_pdv.gui.utils to javafx.fxml;
}
