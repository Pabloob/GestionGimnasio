package com.gestiongimnasio.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasena;

    @NotBlank
    @Email
    @Column(unique = true)
    private String correo;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$")
    private String telefono;

    @NotNull
    @Past
    private LocalDate fechaNacimiento;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    private boolean activo = true;

    public enum TipoUsuario {
        CLIENTE, TRABAJADOR
    }

    @PrePersist
    @PreUpdate
    protected final void validarUsuario() {
        validarTipoUsuario();
        validacionesEspecificas();
    }

    private void validarTipoUsuario() {
        if (tipoUsuario == null) {
            throw new IllegalStateException("El tipo de usuario no puede ser nulo");
        }
    }

    protected abstract void validacionesEspecificas();
}