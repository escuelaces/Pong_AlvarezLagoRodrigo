module desarrollointerfaces.examen.pong {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                        
    opens desarrollointerfaces.examen.pong to javafx.fxml;
    exports desarrollointerfaces.examen.pong;
}