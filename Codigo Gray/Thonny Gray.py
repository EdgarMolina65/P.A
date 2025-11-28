from machine import Pin
import time
import sys


class ControladorLEDs:
    def __init__(self):
        # Pines para LEDs Binarios (lado derecho)
        self.leds_binario = [
            Pin(18, Pin.OUT),  # Bit 0 (LSB)
            Pin(5, Pin.OUT),   # Bit 1
            Pin(17, Pin.OUT),  # Bit 2
            Pin(16, Pin.OUT)   # Bit 3 (MSB)
        ]

        # Pines para LEDs Gray (lado izquierdo)
        self.leds_gray = [
            Pin(26, Pin.OUT),  # Bit 0 (LSB)
            Pin(27, Pin.OUT),  # Bit 1
            Pin(14, Pin.OUT),  # Bit 2
            Pin(12, Pin.OUT)   # Bit 3 (MSB)
        ]

        self.apagar_todos()
        print("Controlador de LEDs inicializado")

    def apagar_todos(self):
        for led in self.leds_binario:
            led.value(0)
        for led in self.leds_gray:
            led.value(0)

    def mostrar_binario(self, bits):
        longitud = len(bits)

        for i in range(4):
            if i < longitud:
                bit_index = longitud - 1 - i
                self.leds_binario[i].value(1 if bits[bit_index] == '1' else 0)
            else:
                self.leds_binario[i].value(0)

    def mostrar_gray(self, bits):
        longitud = len(bits)

        for i in range(4):
            if i < longitud:
                bit_index = longitud - 1 - i
                self.leds_gray[i].value(1 if bits[bit_index] == '1' else 0)
            else:
                self.leds_gray[i].value(0)

    def prueba_leds(self):
        print("Prueba de LEDs Binarios...")
        for led in self.leds_binario:
            led.value(1)
        time.sleep(0.5)

        for led in self.leds_binario:
            led.value(0)

        print("Prueba de LEDs Gray...")
        for led in self.leds_gray:
            led.value(1)
        time.sleep(0.5)

        for led in self.leds_gray:
            led.value(0)

        print("Prueba completada")


def procesar_mensaje(mensaje, controlador):
    try:
        if "BINARIO:" in mensaje and "GRAY:" in mensaje:
            # Extraer binario
            pos_binario = mensaje.find("BINARIO:") + 8
            pos_coma = mensaje.find(",")
            binario = mensaje[pos_binario:pos_coma].strip()

            # Extraer gray
            pos_gray = mensaje.find("GRAY:") + 5
            gray = mensaje[pos_gray:].strip()

            print("Recibido - Binario:", binario, "Gray:", gray)

            controlador.mostrar_binario(binario)
            controlador.mostrar_gray(gray)

        else:
            print("Formato incorrecto")

    except Exception as e:
        print("Error al procesar mensaje:", e)


def main():
    print("\n=== ESP32 - Conversor Binario/Gray ===")
    print("Esperando datos por UART...\n")

    controlador = ControladorLEDs()
    controlador.prueba_leds()

    buffer = ""

    try:
        while True:
            char = sys.stdin.read(1)

            if char:
                if char == '\n':
                    procesar_mensaje(buffer, controlador)
                    buffer = ""
                else:
                    buffer += char

            time.sleep(0.01)

    except KeyboardInterrupt:
        print("\nPrograma detenido")
        controlador.apagar_todos()


if __name__ == "__main__":
    main()


