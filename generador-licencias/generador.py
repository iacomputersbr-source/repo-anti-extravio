import random
import string
import sys

def generar_codigo():
    """Genera codigo formato XXXXX-XXXXX-XXXXX-XXXXX-XXXXX"""
    chars = string.ascii_uppercase + string.digits
    partes = [''.join(random.choice(chars) for _ in range(5)) for _ in range(5)]
    return '-'.join(partes)

def generar_con_rsa():
    try:
        import rsa
        # Si la libreria rsa esta instalada, firma el codigo
        (pub, priv) = rsa.newkeys(512)
        codigo = generar_codigo()
        firma = rsa.sign(codigo.encode(), priv, 'SHA-256')
        print(f"CODIGO: {codigo}")
        print(f"FIRMA RSA (hex): {firma.hex()[:64]}...")
        print(f"CLAVE PUBLICA: {pub}")
        return codigo
    except ImportError:
        print("[!] Libreria rsa no encontrada, usando fallback sin firma")
        return generar_codigo()
    except Exception as e:
        print(f"[!] Error RSA: {e}, usando fallback sin libreria")
        return generar_codigo()

if __name__ == "__main__":
    cantidad = 5
    if len(sys.argv) > 1:
        try:
            cantidad = int(sys.argv[1])
        except:
            pass

    print(f"Generando {cantidad} licencias Anti-Extravio PRO:\n")
    for i in range(cantidad):
        if i == 0:
            codigo = generar_con_rsa()
            # generar_con_rsa ya imprime si tiene rsa, sino imprimimos
            if codigo and "CODIGO:" not in str(codigo):
                # si es fallback puro, imprimir como lista
                print(f"1. {codigo}")
        else:
            print(f"{i+1}. {generar_codigo()}")

    print("\nFormato: XXXXX-XXXXX-XXXXX-XXXXX-XXXXX")
    print("Validacion: Regex ^[A-Z0-9]{5}(-[A-Z0-9]{5}){4}$")
