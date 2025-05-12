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
    exports org.example.delta_pdv.gui;
    opens org.example.delta_pdv.gui to javafx.fxml;
    exports org.example.delta_pdv.gui.controllers;
    opens org.example.delta_pdv.gui.controllers to javafx.fxml;
}