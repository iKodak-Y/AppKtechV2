USE [master]
GO
/****** Object:  Database [BD_KTECH_V2]    Script Date: 11/2/2025 16:12:41 ******/
CREATE DATABASE [BD_KTECH_V2]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BD_KTECH_V2', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\BD_KTECH_V2.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'BD_KTECH_V2_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\BD_KTECH_V2_log.ldf' , SIZE = 73728KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [BD_KTECH_V2] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BD_KTECH_V2].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BD_KTECH_V2] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET ARITHABORT OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BD_KTECH_V2] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BD_KTECH_V2] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET  DISABLE_BROKER 
GO
ALTER DATABASE [BD_KTECH_V2] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BD_KTECH_V2] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET RECOVERY FULL 
GO
ALTER DATABASE [BD_KTECH_V2] SET  MULTI_USER 
GO
ALTER DATABASE [BD_KTECH_V2] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BD_KTECH_V2] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BD_KTECH_V2] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BD_KTECH_V2] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [BD_KTECH_V2] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [BD_KTECH_V2] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'BD_KTECH_V2', N'ON'
GO
ALTER DATABASE [BD_KTECH_V2] SET QUERY_STORE = ON
GO
ALTER DATABASE [BD_KTECH_V2] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [BD_KTECH_V2]
GO
/****** Object:  Table [dbo].[Categorias]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categorias](
	[IDCategoria] [int] IDENTITY(1,1) NOT NULL,
	[Nombre] [varchar](50) NOT NULL,
	[Estado] [char](1) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IDCategoria] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Clientes]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Clientes](
	[IDCliente] [int] IDENTITY(1,1) NOT NULL,
	[Nombre] [varchar](100) NOT NULL,
	[Apellido] [varchar](100) NOT NULL,
	[CedulaRUC] [varchar](20) NOT NULL,
	[Direccion] [text] NULL,
	[Telefono] [varchar](15) NULL,
	[Email] [varchar](100) NULL,
	[Sexo] [char](1) NOT NULL,
	[Estado] [char](1) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IDCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[CedulaRUC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Compras]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Compras](
	[IDCompra] [int] IDENTITY(1,1) NOT NULL,
	[Fecha] [datetime] NOT NULL,
	[Total] [decimal](10, 2) NOT NULL,
	[Proveedor] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IDCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ConfiguracionEmail]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ConfiguracionEmail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[correo_electronico] [varchar](100) NOT NULL,
	[password_app] [varchar](100) NOT NULL,
	[fecha_modificacion] [datetime] NULL,
	[activo] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ConfiguracionSRI]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ConfiguracionSRI](
	[id_configuracion] [int] IDENTITY(1,1) NOT NULL,
	[url_pruebas] [nvarchar](500) NOT NULL,
	[url_produccion] [nvarchar](500) NOT NULL,
	[fecha_vigencia_inicio] [date] NOT NULL,
	[fecha_vigencia_fin] [date] NULL,
	[descripcion] [nvarchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_configuracion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DetalleCompra]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DetalleCompra](
	[IDDetalleCompra] [int] IDENTITY(1,1) NOT NULL,
	[IDCompra] [int] NOT NULL,
	[IDProducto] [int] NOT NULL,
	[Cantidad] [int] NOT NULL,
	[PrecioUnitario] [decimal](10, 2) NOT NULL,
	[Total] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IDDetalleCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DetalleFactura]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DetalleFactura](
	[id_detalle] [int] IDENTITY(1,1) NOT NULL,
	[id_factura] [int] NOT NULL,
	[descripcion] [nvarchar](300) NOT NULL,
	[cantidad] [decimal](18, 6) NOT NULL,
	[precio_unitario] [decimal](18, 6) NOT NULL,
	[subtotal] [decimal](18, 6) NOT NULL,
	[impuestos] [xml] NOT NULL,
	[fecha_registro] [datetime] NULL,
	[IDProducto] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_detalle] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DetalleVenta]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DetalleVenta](
	[IDDetalleVenta] [int] IDENTITY(1,1) NOT NULL,
	[IDVenta] [int] NOT NULL,
	[IDProducto] [int] NOT NULL,
	[Cantidad] [int] NOT NULL,
	[PrecioUnitario] [decimal](10, 2) NOT NULL,
	[Subtotal] [decimal](10, 2) NOT NULL,
	[IVA] [decimal](10, 2) NOT NULL,
	[Total] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IDDetalleVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Emisor]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Emisor](
	[id_emisor] [int] IDENTITY(1,1) NOT NULL,
	[ruc] [char](13) NOT NULL,
	[razon_social] [nvarchar](300) NOT NULL,
	[nombre_comercial] [nvarchar](300) NULL,
	[direccion] [nvarchar](300) NOT NULL,
	[codigo_establecimiento] [char](3) NOT NULL,
	[punto_emision] [char](3) NOT NULL,
	[tipo_ambiente] [char](1) NOT NULL,
	[obligado_contabilidad] [bit] NOT NULL,
	[certificado_path] [nvarchar](500) NOT NULL,
	[contrasena_certificado] [nvarchar](100) NOT NULL,
	[fecha_registro] [datetime] NULL,
	[Logo] [varbinary](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_emisor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[ruc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FacturaElectronica]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FacturaElectronica](
	[id_factura] [int] IDENTITY(1,1) NOT NULL,
	[id_emisor] [int] NOT NULL,
	[clave_acceso] [char](49) NOT NULL,
	[numero_secuencial] [char](9) NOT NULL,
	[fecha_emision] [date] NOT NULL,
	[ruc_comprador] [char](13) NOT NULL,
	[razon_social_comprador] [nvarchar](300) NOT NULL,
	[direccion_comprador] [nvarchar](300) NULL,
	[estado] [nvarchar](20) NOT NULL,
	[fecha_autorizacion] [datetime] NULL,
	[xml_autorizado] [xml] NULL,
	[fecha_registro] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_factura] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[clave_acceso] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FormaPagoFactura]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FormaPagoFactura](
	[id_pago] [int] IDENTITY(1,1) NOT NULL,
	[id_factura] [int] NOT NULL,
	[forma_pago] [nvarchar](50) NOT NULL,
	[valor_pago] [decimal](18, 6) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_pago] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LogErrores]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LogErrores](
	[IdError] [int] IDENTITY(1,1) NOT NULL,
	[ErrorNumber] [int] NOT NULL,
	[ErrorSeverity] [int] NOT NULL,
	[ErrorState] [int] NOT NULL,
	[ErrorProcedure] [varchar](50) NULL,
	[ErrorLine] [int] NULL,
	[ErrorMessage] [text] NOT NULL,
	[FechaError] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IdError] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LogErroresFactura]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LogErroresFactura](
	[id_error] [int] IDENTITY(1,1) NOT NULL,
	[id_factura] [int] NULL,
	[descripcion] [nvarchar](1000) NOT NULL,
	[fecha_error] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_error] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Permisos]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Permisos](
	[id_permiso] [int] IDENTITY(1,1) NOT NULL,
	[nombre_permiso] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_permiso] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Productos]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Productos](
	[IDProducto] [int] IDENTITY(1,1) NOT NULL,
	[Codigo] [varchar](20) NOT NULL,
	[Nombre] [varchar](100) NOT NULL,
	[Precio] [decimal](10, 2) NOT NULL,
	[PVP] [decimal](10, 2) NOT NULL,
	[StockInicial] [int] NOT NULL,
	[StockActual] [int] NOT NULL,
	[IVA] [decimal](5, 2) NOT NULL,
	[Estado] [char](1) NOT NULL,
	[IDCategoria] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[IDProducto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[Codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[id_rol] [int] IDENTITY(1,1) NOT NULL,
	[nombre_rol] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_rol] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles_Permisos]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles_Permisos](
	[id_rol] [int] NOT NULL,
	[id_permiso] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_rol] ASC,
	[id_permiso] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuarios](
	[id_usuario] [int] IDENTITY(1,1) NOT NULL,
	[nombre_completo] [nvarchar](150) NOT NULL,
	[username] [nvarchar](50) NOT NULL,
	[password] [nvarchar](255) NOT NULL,
	[fecha_registro] [datetime] NULL,
	[id_rol] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_usuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ventas]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ventas](
	[IDVenta] [int] IDENTITY(1,1) NOT NULL,
	[Fecha] [datetime] NOT NULL,
	[Total] [decimal](10, 2) NOT NULL,
	[Estado] [varchar](20) NOT NULL,
	[IDCliente] [int] NULL,
	[NumeroSecuencial] [char](9) NULL,
	[id_emisor] [int] NOT NULL,
	[RucComprador] [char](13) NULL,
	[RazonSocialComprador] [nvarchar](300) NULL,
	[DireccionComprador] [nvarchar](300) NULL,
	[FechaEmision] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[IDVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Categorias] ADD  DEFAULT ('A') FOR [Estado]
GO
ALTER TABLE [dbo].[Clientes] ADD  CONSTRAINT [DF_Clientes_Estado]  DEFAULT ('A') FOR [Estado]
GO
ALTER TABLE [dbo].[ConfiguracionEmail] ADD  DEFAULT (getdate()) FOR [fecha_modificacion]
GO
ALTER TABLE [dbo].[ConfiguracionEmail] ADD  DEFAULT ((1)) FOR [activo]
GO
ALTER TABLE [dbo].[DetalleFactura] ADD  DEFAULT (getdate()) FOR [fecha_registro]
GO
ALTER TABLE [dbo].[Emisor] ADD  DEFAULT (getdate()) FOR [fecha_registro]
GO
ALTER TABLE [dbo].[FacturaElectronica] ADD  DEFAULT (getdate()) FOR [fecha_registro]
GO
ALTER TABLE [dbo].[LogErroresFactura] ADD  DEFAULT (getdate()) FOR [fecha_error]
GO
ALTER TABLE [dbo].[Productos] ADD  CONSTRAINT [DF_Productos_Estado]  DEFAULT ('A') FOR [Estado]
GO
ALTER TABLE [dbo].[Usuarios] ADD  DEFAULT (getdate()) FOR [fecha_registro]
GO
ALTER TABLE [dbo].[DetalleCompra]  WITH NOCHECK ADD FOREIGN KEY([IDCompra])
REFERENCES [dbo].[Compras] ([IDCompra])
GO
ALTER TABLE [dbo].[DetalleCompra]  WITH NOCHECK ADD FOREIGN KEY([IDProducto])
REFERENCES [dbo].[Productos] ([IDProducto])
GO
ALTER TABLE [dbo].[DetalleFactura]  WITH CHECK ADD FOREIGN KEY([id_factura])
REFERENCES [dbo].[FacturaElectronica] ([id_factura])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DetalleFactura]  WITH CHECK ADD  CONSTRAINT [FK_DetalleFactura_Productos] FOREIGN KEY([IDProducto])
REFERENCES [dbo].[Productos] ([IDProducto])
GO
ALTER TABLE [dbo].[DetalleFactura] CHECK CONSTRAINT [FK_DetalleFactura_Productos]
GO
ALTER TABLE [dbo].[DetalleVenta]  WITH NOCHECK ADD FOREIGN KEY([IDProducto])
REFERENCES [dbo].[Productos] ([IDProducto])
GO
ALTER TABLE [dbo].[DetalleVenta]  WITH NOCHECK ADD FOREIGN KEY([IDVenta])
REFERENCES [dbo].[Ventas] ([IDVenta])
GO
ALTER TABLE [dbo].[FacturaElectronica]  WITH CHECK ADD FOREIGN KEY([id_emisor])
REFERENCES [dbo].[Emisor] ([id_emisor])
GO
ALTER TABLE [dbo].[FormaPagoFactura]  WITH CHECK ADD FOREIGN KEY([id_factura])
REFERENCES [dbo].[FacturaElectronica] ([id_factura])
GO
ALTER TABLE [dbo].[LogErroresFactura]  WITH CHECK ADD FOREIGN KEY([id_factura])
REFERENCES [dbo].[FacturaElectronica] ([id_factura])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Productos]  WITH NOCHECK ADD FOREIGN KEY([IDCategoria])
REFERENCES [dbo].[Categorias] ([IDCategoria])
GO
ALTER TABLE [dbo].[Roles_Permisos]  WITH CHECK ADD FOREIGN KEY([id_permiso])
REFERENCES [dbo].[Permisos] ([id_permiso])
GO
ALTER TABLE [dbo].[Roles_Permisos]  WITH CHECK ADD FOREIGN KEY([id_rol])
REFERENCES [dbo].[Roles] ([id_rol])
GO
ALTER TABLE [dbo].[Usuarios]  WITH CHECK ADD  CONSTRAINT [FK_Usuarios_Roles] FOREIGN KEY([id_rol])
REFERENCES [dbo].[Roles] ([id_rol])
GO
ALTER TABLE [dbo].[Usuarios] CHECK CONSTRAINT [FK_Usuarios_Roles]
GO
ALTER TABLE [dbo].[Ventas]  WITH NOCHECK ADD  CONSTRAINT [FK_Ventas_Clientes] FOREIGN KEY([IDCliente])
REFERENCES [dbo].[Clientes] ([IDCliente])
GO
ALTER TABLE [dbo].[Ventas] CHECK CONSTRAINT [FK_Ventas_Clientes]
GO
ALTER TABLE [dbo].[Categorias]  WITH NOCHECK ADD  CONSTRAINT [CK_Categorias_Estado] CHECK  (([Estado]='I' OR [Estado]='A'))
GO
ALTER TABLE [dbo].[Categorias] CHECK CONSTRAINT [CK_Categorias_Estado]
GO
ALTER TABLE [dbo].[Clientes]  WITH NOCHECK ADD CHECK  (([Estado]='I' OR [Estado]='A'))
GO
ALTER TABLE [dbo].[Clientes]  WITH NOCHECK ADD CHECK  (([Sexo]='F' OR [Sexo]='M'))
GO
ALTER TABLE [dbo].[DetalleFactura]  WITH CHECK ADD CHECK  (([cantidad]>(0)))
GO
ALTER TABLE [dbo].[DetalleFactura]  WITH CHECK ADD CHECK  (([precio_unitario]>=(0)))
GO
ALTER TABLE [dbo].[DetalleFactura]  WITH CHECK ADD CHECK  (([subtotal]>=(0)))
GO
ALTER TABLE [dbo].[Emisor]  WITH CHECK ADD CHECK  (([tipo_ambiente]='2' OR [tipo_ambiente]='1'))
GO
ALTER TABLE [dbo].[FacturaElectronica]  WITH CHECK ADD CHECK  (([estado]='NAT' OR [estado]='AUT' OR [estado]='PPR'))
GO
ALTER TABLE [dbo].[Productos]  WITH NOCHECK ADD  CONSTRAINT [CK_Productos_Estado] CHECK  (([Estado]='I' OR [Estado]='A'))
GO
ALTER TABLE [dbo].[Productos] CHECK CONSTRAINT [CK_Productos_Estado]
GO
ALTER TABLE [dbo].[Ventas]  WITH NOCHECK ADD CHECK  (([Estado]='Anulada' OR [Estado]='Emitida'))
GO
/****** Object:  StoredProcedure [dbo].[ObtenerSiguienteSecuencial]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ObtenerSiguienteSecuencial]
    @id_emisor INT,
    @secuencial CHAR(9) OUTPUT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Obtener el último secuencial de la factura
        SELECT @secuencial = RIGHT('000000000' + CAST(ISNULL(MAX(CAST(numero_secuencial AS INT)), 0) + 1 AS VARCHAR), 9)
        FROM FacturaElectronica
        WHERE id_emisor = @id_emisor;

        -- Confirmar la transacción
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;

        -- Registrar el error en LogErrores
        INSERT INTO LogErrores (ErrorNumber, ErrorSeverity, ErrorState, ErrorProcedure, ErrorLine, ErrorMessage, FechaError)
        VALUES (ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE(), GETDATE());

        -- Re-lanzar el error para que sea manejado en el cliente
        THROW;
    END CATCH
END;
GO
/****** Object:  StoredProcedure [dbo].[ObtenerSiguienteSecuencialVenta]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ObtenerSiguienteSecuencialVenta]
    @id_emisor INT,
    @secuencial CHAR(9) OUTPUT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Obtener el último secuencial de la venta
        SELECT @secuencial = RIGHT('000000000' + CAST(ISNULL(MAX(CAST(NumeroSecuencial AS INT)), 0) + 1 AS VARCHAR), 9)
        FROM Ventas
        WHERE id_emisor = @id_emisor;

        -- Confirmar la transacción
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;

        -- Registrar el error en LogErrores
        INSERT INTO LogErrores (ErrorNumber, ErrorSeverity, ErrorState, ErrorProcedure, ErrorLine, ErrorMessage, FechaError)
        VALUES (ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE(), GETDATE());

        -- Re-lanzar el error para que sea manejado en el cliente
        THROW;
    END CATCH
END;
GO
/****** Object:  StoredProcedure [dbo].[RegistrarDetalleFactura]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[RegistrarDetalleFactura]
    @id_factura INT,
    @IDProducto INT,
    @descripcion NVARCHAR(300),
    @cantidad DECIMAL(18, 6),
    @precio_unitario DECIMAL(18, 6),
    @subtotal DECIMAL(18, 6),
    @impuestos XML
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Insertar en la tabla DetalleFactura
        INSERT INTO DetalleFactura (id_factura, IDProducto, descripcion, cantidad, precio_unitario, subtotal, impuestos, fecha_registro)
        VALUES (@id_factura, @IDProducto, @descripcion, @cantidad, @precio_unitario, @subtotal, @impuestos, GETDATE());

        -- Confirmar la transacción
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;

        -- Registrar el error en LogErrores
        INSERT INTO LogErrores (ErrorNumber, ErrorSeverity, ErrorState, ErrorProcedure, ErrorLine, ErrorMessage, FechaError)
        VALUES (ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE(), GETDATE());

        -- Re-lanzar el error para que sea manejado en el cliente
        THROW;
    END CATCH
END;
GO
/****** Object:  StoredProcedure [dbo].[RegistrarFactura]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[RegistrarFactura]
    @id_emisor INT,
    @clave_acceso CHAR(49),
    @numero_secuencial CHAR(9),
    @fecha_emision DATE,
    @ruc_comprador CHAR(13),
    @razon_social_comprador NVARCHAR(300),
    @direccion_comprador NVARCHAR(300),
    @estado NVARCHAR(20)
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Insertar en la tabla FacturaElectronica
        INSERT INTO FacturaElectronica (id_emisor, clave_acceso, numero_secuencial, fecha_emision, ruc_comprador, razon_social_comprador, direccion_comprador, estado, fecha_registro)
        VALUES (@id_emisor, @clave_acceso, @numero_secuencial, @fecha_emision, @ruc_comprador, @razon_social_comprador, @direccion_comprador, @estado, GETDATE());

        -- Confirmar la transacción
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;

        -- Registrar el error en LogErrores
        INSERT INTO LogErrores (ErrorNumber, ErrorSeverity, ErrorState, ErrorProcedure, ErrorLine, ErrorMessage, FechaError)
        VALUES (ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE(), GETDATE());

        -- Re-lanzar el error para que sea manejado en el cliente
        THROW;
    END CATCH
END;
GO
/****** Object:  StoredProcedure [dbo].[sp_encera_base_datos]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_encera_base_datos]
AS      
 --Elimina registros de la tabla detalle_factura e inicializa automáticamente el identity    
 TRUNCATE TABLE DetalleFactura;   
 TRUNCATE TABLE DetalleVenta;         
 DELETE FROM Clientes;    
 --Reinicia el identity de la tabla a 0    
 DBCC CHECKIDENT ('dbo.CLIENTES', RESEED, 0)     
 DELETE FROM Productos;    
 DBCC CHECKIDENT ('dbo.PRODUCTOS', RESEED, 0)    
 DELETE FROM Categorias;    
 DBCC CHECKIDENT ('dbo.CATEGORIAS', RESEED, 0)  
      
/*
EXECUTE sp_encera_base_datos
*/
GO
/****** Object:  StoredProcedure [dbo].[sp_InsertarConsumidorFinal]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InsertarConsumidorFinal]
AS
BEGIN
    BEGIN TRY
        -- Verificar si ya existe un Consumidor Final
        IF NOT EXISTS (SELECT 1 FROM Clientes WHERE CedulaRUC = '9999999999999')
        BEGIN
            -- Desactivar la generación automática de identidad
            SET IDENTITY_INSERT Clientes ON;

            -- Insertar Consumidor Final
            INSERT INTO Clientes 
                (IDCliente, Nombre, Apellido, CedulaRUC, Direccion, Telefono, Email, Sexo, Estado)
            VALUES 
                (1, 'Consumidor', 'Final', '9999999999999', 'S/D', 'S/N', '', 'M', 'A');

            -- Reactivar la generación automática de identidad
            SET IDENTITY_INSERT Clientes OFF;

            PRINT 'Consumidor Final insertado correctamente';
        END
        ELSE
        BEGIN
            PRINT 'El Consumidor Final ya existe en la base de datos';
        END
    END TRY
    BEGIN CATCH
        PRINT 'Error al insertar Consumidor Final: ' + ERROR_MESSAGE();
    END CATCH
END;

/*
exec sp_InsertarConsumidorFinal
*/
GO
/****** Object:  StoredProcedure [dbo].[tablas_facturacion]    Script Date: 11/2/2025 16:12:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[tablas_facturacion]
AS
BEGIN
	-- Tabla para almacenar datos del emisor
CREATE TABLE Emisor (
    id_emisor INT IDENTITY(1,1) PRIMARY KEY,
    ruc CHAR(13) NOT NULL UNIQUE,
    razon_social NVARCHAR(300) NOT NULL,
    nombre_comercial NVARCHAR(300),
    direccion NVARCHAR(300) NOT NULL,
    codigo_establecimiento CHAR(3) NOT NULL,
    punto_emision CHAR(3) NOT NULL,
    tipo_ambiente CHAR(1) NOT NULL CHECK (tipo_ambiente IN ('1', '2')), -- 1: Pruebas, 2: Producción
    obligado_contabilidad BIT NOT NULL,
    certificado_path NVARCHAR(500) NOT NULL, -- Ruta del archivo .p12
    contrasena_certificado NVARCHAR(100) NOT NULL,
    fecha_registro DATETIME DEFAULT GETDATE()
);

-- Tabla para almacenar información de las facturas electrónicas
CREATE TABLE FacturaElectronica (
    id_factura INT IDENTITY(1,1) PRIMARY KEY,
    id_emisor INT NOT NULL FOREIGN KEY REFERENCES Emisor(id_emisor),
    clave_acceso CHAR(49) NOT NULL UNIQUE,
    numero_secuencial CHAR(9) NOT NULL,
    fecha_emision DATE NOT NULL,
    ruc_comprador CHAR(13) NOT NULL,
    razon_social_comprador NVARCHAR(300) NOT NULL,
    direccion_comprador NVARCHAR(300),
    estado NVARCHAR(20) NOT NULL CHECK (estado IN ('PPR', 'AUT', 'NAT')), -- PPR: En Procesamiento, AUT: Autorizado, NAT: No Autorizado
    fecha_autorizacion DATETIME,
    xml_autorizado XML, -- XML autorizado por el SRI
    fecha_registro DATETIME DEFAULT GETDATE()
);

-- Tabla para almacenar los detalles de las facturas
CREATE TABLE DetalleFactura (
    id_detalle INT IDENTITY(1,1) PRIMARY KEY,
    id_factura INT NOT NULL FOREIGN KEY REFERENCES FacturaElectronica(id_factura) ON DELETE CASCADE,
    codigo_producto NVARCHAR(50) NOT NULL,
    descripcion NVARCHAR(300) NOT NULL,
    cantidad DECIMAL(18, 6) NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(18, 6) NOT NULL CHECK (precio_unitario >= 0),
    subtotal DECIMAL(18, 6) NOT NULL CHECK (subtotal >= 0),
    impuestos XML NOT NULL, -- Detalle de los impuestos aplicados
    fecha_registro DATETIME DEFAULT GETDATE()
);

-- Tabla para registrar los errores específicos de facturas electrónicas
CREATE TABLE LogErroresFactura (
    id_error INT IDENTITY(1,1) PRIMARY KEY,
    id_factura INT FOREIGN KEY REFERENCES FacturaElectronica(id_factura) ON DELETE CASCADE,
    descripcion NVARCHAR(1000) NOT NULL,
    fecha_error DATETIME DEFAULT GETDATE()
);

-- Tabla para configurar parámetros generales del SRI
CREATE TABLE ConfiguracionSRI (
    id_configuracion INT IDENTITY(1,1) PRIMARY KEY,
    url_pruebas NVARCHAR(500) NOT NULL,
    url_produccion NVARCHAR(500) NOT NULL,
    fecha_vigencia_inicio DATE NOT NULL,
    fecha_vigencia_fin DATE,
    descripcion NVARCHAR(500)
);

CREATE TABLE FormaPagoFactura (
    id_pago INT IDENTITY(1,1) PRIMARY KEY,
    id_factura INT NOT NULL,
    forma_pago NVARCHAR(50) NOT NULL,
    valor_pago DECIMAL(18, 6) NOT NULL,
    FOREIGN KEY (id_factura) REFERENCES FacturaElectronica(id_factura)
);


END

/**
EXEC tablas_facturacion
**/
GO
USE [master]
GO
ALTER DATABASE [BD_KTECH_V2] SET  READ_WRITE 
GO
