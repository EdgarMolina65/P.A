import serial
import time


class ConversorBinarioGray:
    """Clase para convertir números binarios a código Gray"""

    def __init__(self, puerto='COM5', baudrate=115200):
        """
        Inicializa el conversor y la conexión serial
        Args:
            puerto: Puerto serial (ej: 'COM5' en Windows, '/dev/ttyUSB0' en Linux)
            baudrate: Velocidad de comunicación (115200 para MicroPython)
        """
        self.puerto = puerto
        self.baudrate = baudrate
        self.serial_conn = None

    def conectar_serial(self):
        """Establece la conexión con el ESP32"""
        try:
            self.serial_conn = serial.Serial(
                self.puerto,
                self.baudrate,
                timeout=1,
                write_timeout=1
            )
            time.sleep(2)  # Esperar a que el ESP32 se inicialice
            print(f"Conectado al puerto {self.puerto} a {self.baudrate} baudios")

            # Limpiar buffer
            self.serial_conn.reset_input_buffer()
            self.serial_conn.reset_output_buffer()

            return True
        except serial.SerialException as e:
            print(f"Error al conectar: {e}")
            print("Verifica que:")
            print("1. El ESP32 esté conectado")
            print("2. El puerto sea el correcto")
            print("3. Thonny no esté usando el puerto")
            return False

    def binario_a_gray(self, binario):
        """
        Convierte un número binario a código Gray
        Args:
            binario: String con el número en binario (ej: '1010')
        Returns:
            String con el código Gray
        """
        if not binario or not all(c in '01' for c in binario):
            raise ValueError("El binario debe contener solo 0s y 1s")

        # El primer bit es igual
        gray = binario[0]

        # XOR entre bits consecutivos
        for i in range(len(binario) - 1):
            bit_actual = int(binario[i])
            bit_siguiente = int(binario[i + 1])
            gray += str(bit_actual ^ bit_siguiente)

        return gray

    def enviar_datos(self, binario, gray):
        """
        Envía los datos al ESP32
        Args:
            binario: String binario
            gray: String código Gray
        """
        if self.serial_conn and self.serial_conn.is_open:
            try:
                # Formato: "BINARIO:1010,GRAY:1111\n"
                mensaje = f"BINARIO:{binario},GRAY:{gray}\n"
                self.serial_conn.write(mensaje.encode('utf-8'))
                self.serial_conn.flush()  # Asegurar que se envíe
                print(f"✓ Enviado: {mensaje.strip()}")

                # Leer respuesta del ESP32 si existe
                time.sleep(0.1)
                if self.serial_conn.in_waiting > 0:
                    respuesta = self.serial_conn.readline().decode('utf-8', errors='ignore').strip()
                    if respuesta:
                        print(f"  ESP32: {respuesta}")
            except Exception as e:
                print(f"✗ Error al enviar: {e}")
        else:
            print("✗ Error: No hay conexión serial")

    def procesar_numero(self, binario):
        """
        Procesa un número binario completo: convierte y envía
        Args:
            binario: String con el número binario
        """
        try:
            gray = self.binario_a_gray(binario)
            print(f"\n{'=' * 40}")
            print(f"Binario: {binario}")
            print(f"Gray:    {gray}")
            print(f"{'=' * 40}")
            self.enviar_datos(binario, gray)
            return gray
        except ValueError as e:
            print(f"✗ Error: {e}")
            return None

    def cerrar_conexion(self):
        """Cierra la conexión serial"""
        if self.serial_conn and self.serial_conn.is_open:
            self.serial_conn.close()
            print("\n✓ Conexión cerrada")


# Programa principal
def main():
    print("\n" + "=" * 50)
    print(" CONVERSOR BINARIO A CÓDIGO GRAY")
    print(" ESP32 con MicroPython")
    print("=" * 50)

    # Crear instancia del conversor
    # IMPORTANTE: Cambia 'COM5' por tu puerto correcto
    conversor = ConversorBinarioGray(puerto='COM5', baudrate=115200)

    # Conectar al ESP32
    print("\nIntentando conectar al ESP32...")
    if not conversor.conectar_serial():
        print("\n✗ No se pudo conectar.")
        print("\nPASOS A SEGUIR:")
        print("1. Cierra Thonny si está abierto")
        print("2. Verifica el puerto en Administrador de dispositivos")
        print("3. Cambia 'COM5' en el código por tu puerto")
        print("4. Ejecuta este script nuevamente")
        return

    print("\n✓ Conexión exitosa!")
    print("\nINSTRUCCIONES:")
    print("- Ingresa números en binario (4 bits recomendado)")
    print("- Escribe 'salir' para terminar")
    print("- Ejemplos: 0000, 0001, 1010, 1111")
    print("-" * 50)

    try:
        while True:
            entrada = input("\nBinario (4 bits): ").strip()

            if entrada.lower() == 'salir':
                break

            if not entrada:
                continue

            # Asegurar que tenga 4 bits
            if len(entrada) < 4:
                entrada = entrada.zfill(4)
                print(f"  → Ajustado a 4 bits: {entrada}")
            elif len(entrada) > 4:
                print(f"  ⚠ Advertencia: Usando solo los primeros 4 bits")
                entrada = entrada[:4]

            conversor.procesar_numero(entrada)
            time.sleep(0.3)  # Pausa para estabilidad

    except KeyboardInterrupt:
        print("\n\n⚠ Interrumpido por el usuario")
    finally:
        conversor.cerrar_conexion()
        print("\n✓ Programa finalizado")


if __name__ == "__main__":
    main()
