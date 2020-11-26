/* LOS ID NO SERÁN AUTOINCREMENT CON EL PROPÓSITO DE SER USADOS COMO CLAVES FORÁNEAS, SE LES ASIGNARÁ ID CON INCREMENTO DESDE JAVA O PHP */

CREATE TABLE IF NOT EXISTS Usuario (
    id_usuario TINYINT(3) unsigned NOT NULL, /* Usuario es el psicólogo o psiquiatra que se registra */
    nombres VARCHAR(50) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    tipo_usuario VARCHAR(10) NOT NULL, /* Se registrará con un spinner si es psicólogo o psiquiatra */
    PRIMARY KEY(id_usuario)
)engine=InnoDB default charset=UTF8 default collate=utf8_unicode_ci;

create table if not exists Paciente (
    id_paciente TINYINT(3) unsigned not null,
    fecha_registro DATE not null,
    nombres VARCHAR(50) not null,
    nombre_pmt VARCHAR(50), /* Nombre de padre, madre o tutor en caso de ser menor de edad */
    telefono int(15),
    estado VARCHAR(25),
    municipio VARCHAR(25),
    domicilio VARCHAR(80),
    sexo VARCHAR(20),
    fecha_nacimiento DATE,
    estado_civil VARCHAR(25),
    escolaridad VARCHAR(25),
    ocupacion VARCHAR(25), 
    PRIMARY KEY(id_paciente)
)engine=InnoDB default charset=UTF8 default collate=utf8_unicode_ci;

/* El caso en el que se está trabajando, en caso de que haya dos o más pacientes del mismo */
create table if not exists Caso (
    id_caso TINYINT(3) unsigned not null,
    fecha_apertura DATE,
    descripcion_general VARCHAR(500),
    estado TINYINT(4),
    PRIMARY KEY(id_caso)
)engine=InnoDB default charset=UTF8 default collate=utf8_unicode_ci;

create table if not exists Cita (
    id_cita TINYINT(3) unsigned not null,
    fecha DATE,
    hora TIME,
    paciente TINYINT(3) unsigned default null, /* No será un campo obligatorio, en caso de que sea nuevo paciente, simplemente se ingresará el nombre */
    nombre VARCHAR(50),
    usuario TINYINT(3) unsigned,
    asistio int(1) default null, /* Al pasar la hora de la cita, si no es creada ninguna ocurrencia de "Consulta" se asignará 0 que significa que no asistió, y viceversa con 1 */
    PRIMARY KEY (id_cita),
    INDEX fk_usuarioC_idx (usuario ASC),
    INDEX fk_pacienteC_idx (paciente ASC),
    CONSTRAINT fk_usuarioC
        FOREIGN KEY (usuario)
        REFERENCES Usuario (id_usuario)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_pacienteC
        FOREIGN KEY (paciente)
        REFERENCES Paciente (id_paciente)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
)engine=InnoDB default charset=UTF8 default collate=utf8_unicode_ci;

create table if not exists Consulta (
    id_consulta TINYINT(3) unsigned not null,
    usuario TINYINT(3) unsigned not null,
    cita TINYINT(3) unsigned, /* En caso de ser con cita */
    caso TINYINT(3) unsigned not null,
    paciente TINYINT(3) unsigned not null,
    fecha DATE not null,
    hora TIME not null,
    motivo_atencion VARCHAR(500) not null,
    notas_sesion VARCHAR(500) not null, 
    tipo_consulta VARCHAR(25) not null, /* consulta de psicología o pisquiatría */
    tratamiento VARCHAR(500), /* En caso de ser consulta de psiquiatria */
    PRIMARY KEY(id_consulta),
    INDEX fk_usuario_idx (usuario ASC),
    INDEX fk_caso_idx (caso ASC),
    INDEX fk_paciente_idx (paciente ASC),
    INDEX fk_cita_idx (cita ASC),
    CONSTRAINT fk_usuario
        FOREIGN KEY (usuario)
        REFERENCES Usuario (id_usuario)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_caso
        FOREIGN KEY (caso)
        REFERENCES Caso (id_caso)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_paciente
        FOREIGN KEY (paciente)
        REFERENCES Paciente (id_paciente)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_cita
        FOREIGN KEY (cita)
        REFERENCES Cita (id_cita)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
)engine=InnoDB default charset=UTF8 default collate=utf8_unicode_ci;