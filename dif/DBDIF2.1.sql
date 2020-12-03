/* LOS ID NO SERÁN AUTOINCREMENT CON EL PROPÓSITO DE SER USADOS COMO CLAVES FORÁNEAS, SE LES ASIGNARÁ ID CON INCREMENTO DESDE JAVA O PHP */

CREATE TABLE IF NOT EXISTS Usuario (
    id_usuario TINYINT(3) unsigned NOT NULL, /* Usuario es el psicólogo o psiquiatra que se registra */
    nombre VARCHAR(50) NOT NULL,
    ap VARCHAR(50) NOT NULL, /* apellido paterno */
    am VARCHAR(50) NOT NULL, /* apellido materno */
    correo VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    tipo_usuario VARCHAR(10) NOT NULL, /* Se registrará con un spinner si es psicólogo, psiquiatra o recepcionista(persona que hace las citas) */
    PRIMARY KEY(id_usuario)
)engine=InnoDB default charset=UTF8 default collate=utf8_unicode_ci;

create table if not exists Paciente (
    id_paciente SMALLINT(5) unsigned not null,
    fecha_registro DATE not null,
    nombre VARCHAR(50) not null,
    ap VARCHAR(50) NOT NULL, /* apellido paterno */
    am VARCHAR(50) NOT NULL, /* apellido materno */
    menor_de_edad TINYINT(1) unsigned DEFAULT 0,
    nombre_pmt VARCHAR(50), /* Nombre de padre, madre o tutor en caso de ser menor de edad */
    ap_pmt VARCHAR(50),
    am_pmt VARCHAR(50),
    telefono VARCHAR(15),
    estado VARCHAR(25),
    municipio VARCHAR(25),
    localidad VARCHAR(50),
    calle VARCHAR(50),
    numero_casa VARCHAR(10),
    cp VARCHAR(5),
    sexo VARCHAR(20),
    fecha_nacimiento DATE,
    estado_civil VARCHAR(25),
    escolaridad VARCHAR(25),
    ocupacion VARCHAR(25), 
    PRIMARY KEY(id_paciente)
)engine=InnoDB default charset=UTF8 default collate=utf8_unicode_ci;

/* El caso en el que se está trabajando, en caso de que haya dos o más pacientes del mismo */
create table if not exists Caso (
    id_caso SMALLINT(5) unsigned not null,
    fecha_apertura DATE,
    descripcion_general VARCHAR(500),
    estado TINYINT(1) unsigned,
    tipo_caso VARCHAR(15), /* Peritaje, psicología, psquiatría */
    PRIMARY KEY(id_caso)
)engine=InnoDB default charset=UTF8 default collate=utf8_unicode_ci;

create table if not exists Cita (
    id_cita SMALLINT(5) unsigned not null,
    fecha DATE,
    hora TIME,
    paciente SMALLINT(5) unsigned, /* En caso de que sea nuevo paciente y no exista, se creará uno nuevo automáticamente */
    usuario TINYINT(3) unsigned,
    asistio TINYINT(1) unsigned default null, /* Al pasar la hora de la cita, si no es creada ninguna ocurrencia de "Consulta" se asignará 0 que significa que no asistió, y viceversa con 1 */
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
    id_consulta SMALLINT(5) unsigned not null,
    usuario TINYINT(3) unsigned not null,
    cita SMALLINT(5) unsigned, /* En caso de ser con cita */
    caso SMALLINT(5) unsigned not null,
    paciente SMALLINT(5) unsigned not null,
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

create table if not exists Sesion_peritaje (
    id_peritaje SMALLINT(5) unsigned not null,
    usuario TINYINT(3) unsigned not null,
    caso SMALLINT(5) unsigned not null,
    nombre_paciente VARCHAR(50) not null,
    ap_paciente VARCHAR(50) not null,
    am_paciente VARCHAR(50) not null,
    menor_de_edad TINYINT(1) unsigned DEFAULT 0,
    nombre_pmt VARCHAR(50), /* Nombre de padre, madre o tutor en caso de ser menor de edad */
    fecha DATE not null,
    hora DATE not null,
    tipo_consulta VARCHAR(50), /* Test, entrevista, evaluación, etc. No sé exactamente si una sesión sólo consistiría en una sola de esas opciones o varias, para ver cómo lo implementamos en la app */
    motivo_atencion VARCHAR(500),
    notas_sesion VARCHAR(500),
    PRIMARY KEY(id_peritaje),
    INDEX fk_usuarioP_idx (usuario ASC),
    INDEX fk_casoP_idx (caso ASC),
    CONSTRAINT fk_usuarioP
        FOREIGN KEY (usuario)
        REFERENCES Usuario (id_usuario)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_casoP
        FOREIGN KEY (caso)
        REFERENCES Caso (id_caso)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
)engine=InnoDB default charset=UTF8 default collate=utf8_unicode_ci;