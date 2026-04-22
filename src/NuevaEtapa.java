import java.sql.*;
import java.util.*;

public class NuevaEtapa {

    public static void insertarEtapaCompleta() {

        Scanner sc = new Scanner(System.in);
        Connection con = null;

        try {
            con = ConexionBD.conectar();

            // DATOS
            System.out.print("Numero etapa: ");
            int numero = sc.nextInt();
            sc.nextLine();

            System.out.print("Origen: ");
            String origen = sc.nextLine();

            System.out.print("Destino: ");
            String destino = sc.nextLine();

            System.out.print("Distancia km: ");
            double km = sc.nextDouble();
            sc.nextLine();

            System.out.print("Fecha (YYYY-MM-DD): ");
            String fecha = sc.nextLine();

            // INICIAR TRANSACCION
            con.setAutoCommit(false);

            // INSERT ETAPA
            String sqlEtapa = "INSERT INTO ETAPA(numero, origen, destino, km, fecha) VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'))";
            PreparedStatement psEtapa = con.prepareStatement(sqlEtapa);

            psEtapa.setInt(1, numero);
            psEtapa.setString(2, origen);
            psEtapa.setString(3, destino);
            psEtapa.setDouble(4, km);
            psEtapa.setString(5, fecha);

            psEtapa.executeUpdate();

            // OBTENER CICLISTAS
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_ciclista FROM CICLISTA");

            List<Integer> ciclistas = new ArrayList<>();

            while (rs.next()) {
                ciclistas.add(rs.getInt("id_ciclista"));
            }

            if (ciclistas.isEmpty()) {
                throw new SQLException("No hay ciclistas");
            }

            // mezcla de psosicionas random
            Collections.shuffle(ciclistas);

            int[] puntosTabla = {100, 90, 80, 70, 60};

            String sqlPart = "INSERT INTO PARTICIPACION(numero_etapa, id_ciclista, posicion, puntos) VALUES (?, ?, ?, ?)";
            PreparedStatement psPart = con.prepareStatement(sqlPart);

            // inset de particion
            for (int i = 0; i < ciclistas.size(); i++) {

                int puntos = (i < puntosTabla.length) ? puntosTabla[i] : 0;

                psPart.setInt(1, numero);
                psPart.setInt(2, ciclistas.get(i));
                psPart.setInt(3, i + 1);
                psPart.setInt(4, puntos);

                psPart.executeUpdate();
            }

            // commit
            con.commit();

            System.out.println("Etapa " + numero + " insertada correctamente");
            System.out.println("Ciclistas añadidos: " + ciclistas.size());
            System.out.println("Fecha: " + fecha);

        } catch (Exception e) {

            System.out.println("Etapa cancelada por error. No se guardaron los datos.");

            try {
                if (con != null) con.rollback(); // 🔥 rollback correcto
            } catch (SQLException ex) {
                System.out.println("Error en rollback");
            }

        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {}
        }
    }
}
