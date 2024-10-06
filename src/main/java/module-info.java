module com.bib.hospitalproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.management;


    opens com.bib.hospitalproject.data to
            javafx.base;

    opens com.bib.hospitalproject to javafx.fxml;
    exports com.bib.hospitalproject;
}