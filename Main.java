package paq;

import java.sql.*;
import java.util.*;

public class Main {

	private static final String URL = "jdbc:sqlite:biblioteca.db";
	static Scanner scLine = new Scanner(System.in);
	static Scanner scInt = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try (Connection conn = DriverManager.getConnection(URL)) {

			System.out.println("Conexión establecida correctamente");

			String sql = """
					CREATE TABLE IF NOT EXISTS libros (
					id INTEGER PRIMARY KEY AUTOINCREMENT,
					titulo TEXT NOT NULL,
					autor TEXT NOT NULL,
					anio INTEGER NOT NULL,
					genero TEXT NOT NULL,
					disponible INTEGER NOT NULL
					);
					""";

			try (Statement stmt = conn.createStatement()) {

				stmt.execute(sql);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Hay un error en la sentencia SQL" + " con la que creamos la tabla");
			}

			boolean salir = false;

			do {
				System.out.println("\n---MENÚ---");
				System.out.println("1.-Añadir libro");
				System.out.println("2.-Listar todos los libros");
				System.out.println("3.-Buscar libro");
				System.out.println("4.-Actualizar libro");
				System.out.println("5.-Eliminar libro");
				System.out.println("6.-Listar libros disponibles");
				System.out.println("7.-Búsqueda avanzada");
				System.out.println("8.-Listar libros ordenados");
				System.out.println("0.-Salir");
				System.out.println("Elige una opción: ");
				int opcion = scInt.nextInt();

				switch (opcion) {

				case 1:
					aniadirLibro(conn);
					break;

				case 2:
					listarLibros(conn);
					break;

				case 3:
					buscarLibro(conn);
					break;

				case 4:
					actualizarLibro(conn);
					break;

				case 5:
					eliminarLibro(conn);
					break;

				case 6:
					listarLibrosDisponibles(conn);
					break;

				case 7:
					busquedaAvanzada(conn);
					break;

				case 8:
					librosOrdenados(conn);
					break;

				case 0:
					System.out.println("Saliendo del menú...");
					salir = true;
					break;
				}

			} while (!salir);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("No se ha podido establecer la conexión");
		}

	}

	private static void librosOrdenados(Connection conn) {
		// TODO Auto-generated method stub

		System.out.println("LISTA DE LIBROS ORDENADOS");
		System.out.println("1.-Ordenar por título");
		System.out.println("2.-Ordenar por autor");
		System.out.println("3.-Ordenar por año");
		System.out.println("Elige una opción: ");
		int op = scInt.nextInt();

		switch (op) {

		case 1:
			String sql = "SELECT * FROM libros ORDER BY titulo ASC";

			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

				while (rs.next()) {

					String disponibilidad;
					if (rs.getInt("disponible") == 1) {
						disponibilidad = "Disponible";
					} else {
						disponibilidad = "No disponible";
					}

					System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
							+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error en la lectura");
			}
			break;

		case 2:
			String sql2 = "SELECT * FROM libros ORDER BY autor ASC";

			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql2)) {

				while (rs.next()) {

					String disponibilidad;
					if (rs.getInt("disponible") == 1) {
						disponibilidad = "Disponible";
					} else {
						disponibilidad = "No disponible";
					}

					System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
							+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error en la lectura");
			}
			break;

		case 3:
			String sql3 = "SELECT * FROM libros ORDER BY anio ASC";

			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql3)) {

				while (rs.next()) {

					String disponibilidad;
					if (rs.getInt("disponible") == 1) {
						disponibilidad = "Disponible";
					} else {
						disponibilidad = "No disponible";
					}

					System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
							+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error en la lectura");
			}
			break;

		}

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();

	}

	private static void busquedaAvanzada(Connection conn) {
		// TODO Auto-generated method stub

		System.out.println("Búsqueda Avanzada");

		System.out.println("Autor?");
		String autorBusq = scLine.nextLine();

		System.out.println("Genero?");
		String generoBusq = scLine.nextLine();

		System.out.println("Disponibilidad? 1= disponible, 0 = no disponible");
		int disponibleBusq = scInt.nextInt();

		String sql = "SELECT * FROM libros WHERE autor LIKE ? AND genero LIKE ? AND disponible = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, autorBusq);
			pstmt.setString(2, generoBusq);
			pstmt.setInt(3, disponibleBusq);
			ResultSet rs = pstmt.executeQuery();

			boolean encontrado = false;
			while (rs.next()) {
				encontrado = true;
				String disponibilidad;
				if (rs.getInt("disponible") == 1) {
					disponibilidad = "Disponible";
				} else {
					disponibilidad = "No disponible";
				}
				System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
						+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad);
			}
			if (!encontrado) {
				System.out.println("Libro no encontrado");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en la búsqueda");
		}

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();
	}

	private static void listarLibrosDisponibles(Connection conn) {
		// TODO Auto-generated method stub

		System.out.println("LISTADO DE LIBROS DISPONIBLES");

		String sql = "SELECT * FROM libros WHERE disponible = 1";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {

				String disponibilidad;
				if (rs.getInt("disponible") == 1) {
					disponibilidad = "Disponible";
				} else {
					disponibilidad = "No disponible";
				}

				System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
						+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad

				);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en la lectura");
		}

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();

	}

	private static void eliminarLibro(Connection conn) {
		// TODO Auto-generated method stub

		String sql = "SELECT * FROM libros";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {

				String disponibilidad;
				if (rs.getInt("disponible") == 1) {
					disponibilidad = "Disponible";
				} else {
					disponibilidad = "No disponible";
				}

				System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
						+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en la lectura");
		}

		System.out.println("\nIntroduce el id del usuario a eliminar\n");
		int idElim = scInt.nextInt();

		String sqlD = "DELETE FROM libros WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sqlD)) {

			pstmt.setInt(1, idElim);

			System.out.println("Seguro de que quiere eliminar? (s/n)");
			String eliminar = scLine.nextLine();

			if (eliminar.equalsIgnoreCase("s")) {
				int filasAfectadas = pstmt.executeUpdate();
				System.out.println("\nSe han eliminado " + filasAfectadas + " libros");
			} else {
				System.out.println("No se han eliminado filas");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al intentar eliminar el libro");
		}

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();

	}

	private static void actualizarLibro(Connection conn) {
		// TODO Auto-generated method stub

		System.out.println("\nIntroduce el id del registro a modificar:");
		int idAct = scInt.nextInt();

		String sql = "SELECT * FROM libros WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idAct);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				String tituloActual = rs.getString("titulo");
				String autorActual = rs.getString("autor");
				int anioActual = rs.getInt("anio");
				String generoActual = rs.getString("genero");
				int disponibleActual = rs.getInt("disponible");

				System.out.println(tituloActual + " | " + autorActual + " |" + anioActual + " | " + generoActual + " |"
						+ disponibleActual);

				System.out.println("Introduce el nuevo título : ");
				String tituloNuevo = scLine.nextLine();
				if (tituloNuevo.equals(""))
					tituloNuevo = tituloActual;

				System.out.println("Introduce el nuevo autor: ");
				String autorNuevo = scLine.nextLine();
				if (autorNuevo.equals(""))
					autorNuevo = autorActual;

				System.out.println("Introduce el nuevo año de publicación: ");
				String anioNuevo = scLine.nextLine();
				int anioN;
				if (anioNuevo.equals("")) {
					anioN = anioActual;
				} else {
					anioN = Integer.valueOf(anioNuevo);
					while (anioN < 1500 || anioN > 2026) {
						System.out.println("Valor no válido. Introduce año entre 1500 y 2026");
						anioN = Integer.valueOf(scLine.nextLine());
					}

				}

				System.out.println("Introduce el nuevo género: ");
				String generoNuevo = scLine.nextLine();
				if (generoNuevo.equals("")) {
					generoNuevo = generoActual;
				}

				System.out.println("Disponibilidad (1 = disponible, 0 = no disponible): ");
				String dispoNuevo = scLine.nextLine();
				int dispoN;

				if (dispoNuevo.equals("")) {
					dispoN = disponibleActual;
				} else {
					dispoN = Integer.valueOf(dispoNuevo);
				}

				while (dispoN != 0 && dispoN != 1) {
					System.out.println("Valor no válido. Introduce 1 (disponible) o 0 (no disponible): ");
					dispoN = scInt.nextInt();
				}

				String sql2 = "UPDATE libros SET titulo = ?, autor  = ?, anio = ?,  genero = ?,  disponible = ? WHERE id = ?";

				try (PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

					pstmt2.setString(1, tituloNuevo);
					pstmt2.setString(2, autorNuevo);
					pstmt2.setInt(3, anioN);
					pstmt2.setString(4, generoNuevo);
					pstmt2.setInt(5, dispoN);
					pstmt2.setInt(6, idAct);

					int filas = pstmt2.executeUpdate();

					System.out.println("Se han actualizado " + filas + " filas");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Error al actualizar la tabla");
				}

			} else {
				System.out.println("No existe libro con ese id.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en la actualiación");
		}

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();

	}

	private static void buscarLibro(Connection conn) {
		// TODO Auto-generated method stub

		System.out.println("1.-Buscar por ID");
		System.out.println("2.-Buscar por título");
		System.out.println("3.-Buscar por autor");
		System.out.println("Elige una opción: ");
		int opcion = scInt.nextInt();

		switch (opcion) {
		case 1:

			System.out.println("\nDime el id para buscar");
			int idBusq = scInt.nextInt();

			String sql = "SELECT * FROM libros WHERE Id=?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setInt(1, idBusq);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {

					String disponibilidad;
					if (rs.getInt("disponible") == 1) {
						disponibilidad = "Disponible";
					} else {
						disponibilidad = "No disponible";
					}

					System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
							+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad);
				} else {
					System.out.println("Libro no encontrado");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error en la búsqueda");
			}

			break;

		case 2:

			System.out.println("Dime un título del cual hacer la búsqueda");
			String tituloBusq = scLine.nextLine();

			String sql2 = "SELECT * FROM libros WHERE titulo LIKE ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {

				pstmt.setString(1, tituloBusq);
				ResultSet rs = pstmt.executeQuery();

				boolean encontrado = false;
				while (rs.next()) {
					encontrado = true;
					String disponibilidad;
					if (rs.getInt("disponible") == 1) {
						disponibilidad = "Disponible";
					} else {
						disponibilidad = "No disponible";
					}

					System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
							+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad);
				}
				if (!encontrado) {
					System.out.println("Libro no encontrado");
				}
			}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error en la búsqueda");
			}
			break;

		case 3:

			System.out.println("Dime el nombre del autor del cual hacer la búsqueda");
			String autorBusq = scLine.nextLine();

			String sql3 = "SELECT * FROM libros WHERE autor LIKE ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql3)) {

				pstmt.setString(1, "%" + autorBusq + "%");
				ResultSet rs = pstmt.executeQuery();

				boolean encontrado = false;

				while (rs.next()) {
					encontrado = true;
					String disponibilidad;
					if (rs.getInt("disponible") == 1) {
						disponibilidad = "Disponible";
					} else {
						disponibilidad = "No disponible";
					}

					System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
							+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad);

				}
				if (!encontrado) {
					System.out.println("Libro no encontrado");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error en la búsqueda");
			}
			break;

		}

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();

	}

	private static void listarLibros(Connection conn) {
		// TODO Auto-generated method stub

		String sql = "SELECT * FROM libros";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {

				String disponibilidad;
				if (rs.getInt("disponible") == 1) {
					disponibilidad = "Disponible";
				} else {
					disponibilidad = "No disponible";
				}

				System.out.println(rs.getInt("Id") + " | " + rs.getString("titulo") + " |" + rs.getString("autor")
						+ " | " + rs.getInt("anio") + " |" + rs.getString("genero") + " |" + disponibilidad);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en la lectura");
		}

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();
	}

	private static void aniadirLibro(Connection conn) {
		// TODO Auto-generated method stub

		System.out.println("Introduce el titulo:");
		String titulo = scLine.nextLine();

		System.out.println("Introduce el autor:");
		String autor = scLine.nextLine();

		System.out.println("Introduce el año de publicación:");
		int anio = scInt.nextInt();

		while (anio < 1500 || anio > 2026) {
			System.out.println("Valor no válido. Introduce año entre 1500 y 2026");
			anio = scInt.nextInt();
		}

		System.out.println("Introduce el género:");
		String genero = scLine.nextLine();

		System.out.println("Disponibilidad (1 = disponible, 0 = no disponible): ");
		int disponible = scInt.nextInt();

		while (disponible != 0 && disponible != 1) {
			System.out.println("Valor no válido. Introduce 1 (disponible) o 0 (no disponible): ");
			disponible = scInt.nextInt();
		}

		if (!titulo.isEmpty() && !autor.isEmpty() && !genero.isEmpty()) {

			String sql1 = "SELECT COUNT(*) FROM libros WHERE titulo = ? AND autor = ? AND anio = ?";
			try (PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
				pstmt1.setString(1, titulo);
				pstmt1.setString(2, autor);
				pstmt1.setInt(3, anio);
				ResultSet rs = pstmt1.executeQuery();
				rs.next();

				if (rs.getInt(1) > 0) {
					System.out.println("Ya existe un libro con ese título, autor y año.");
				} else {

					String sql = "INSERT INTO libros(titulo, autor, anio, genero, disponible) VALUES(?, ?, ?, ?, ?)";
					try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

						pstmt.setString(1, titulo);
						pstmt.setString(2, autor);
						pstmt.setInt(3, anio);
						pstmt.setString(4, genero);
						pstmt.setInt(5, disponible);
						pstmt.executeUpdate();

						System.out.println("Libro " + titulo + " añadido");
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("No se puede añadir libro");
			}

		}

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();

	}

}
