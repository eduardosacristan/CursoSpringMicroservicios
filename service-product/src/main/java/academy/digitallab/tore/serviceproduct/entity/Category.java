package academy.digitallab.tore.serviceproduct.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;

@Entity // Anotamos con Entity para indicar que es una entidad
@Table (name = "tbl_categories") // Anotamos con table porque la tabla de la base de datos no se llama igual
@Data // Genera automaticamente getters y setters , tambien  hashcode y toString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id //Identifica el attributo como la clave primaria
    @GeneratedValue (strategy = GenerationType.IDENTITY) // Indica que es autoincremental
    private Long id;
    private String name;
}
