import 'dart:math';
import 'package:flutter/material.dart';
import 'package:forui/forui.dart';

void main() {
  runApp(const CalculadoraApp());
}

class CalculadoraApp extends StatelessWidget {
  const CalculadoraApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Calculadora Forui',
      supportedLocales: FLocalizations.supportedLocales,
      localizationsDelegates: FLocalizations.localizationsDelegates,
      builder: (context, child) => FTheme(
        data: FThemes.zinc.light.touch,
        child: child!,
      ),
      home: const CalculadoraPage(),
    );
  }
}

class CalculadoraPage extends StatefulWidget {
  const CalculadoraPage({super.key});

  @override
  State<CalculadoraPage> createState() => _CalculadoraPageState();
}

class _CalculadoraPageState extends State<CalculadoraPage> {
  final TextEditingController numero1Controller = TextEditingController();
  final TextEditingController numero2Controller = TextEditingController();

  String resultado = 'Resultado:';

  @override
  void dispose() {
    numero1Controller.dispose();
    numero2Controller.dispose();
    super.dispose();
  }

  void calcular(String operacion) {
    final double? numero1 = double.tryParse(numero1Controller.text);
    final double? numero2 = double.tryParse(numero2Controller.text);

    if (numero1 == null) {
      setState(() {
        resultado = 'Ingrese un primer número válido';
      });
      return;
    }

    switch (operacion) {
      case 'suma':
        if (numero2 == null) {
          setState(() {
            resultado = 'Ingrese un segundo número válido';
          });
          return;
        }
        setState(() {
          resultado = 'Resultado: ${numero1 + numero2}';
        });
        break;

      case 'resta':
        if (numero2 == null) {
          setState(() {
            resultado = 'Ingrese un segundo número válido';
          });
          return;
        }
        setState(() {
          resultado = 'Resultado: ${numero1 - numero2}';
        });
        break;

      case 'multiplicacion':
        if (numero2 == null) {
          setState(() {
            resultado = 'Ingrese un segundo número válido';
          });
          return;
        }
        setState(() {
          resultado = 'Resultado: ${numero1 * numero2}';
        });
        break;

      case 'division':
        if (numero2 == null) {
          setState(() {
            resultado = 'Ingrese un segundo número válido';
          });
          return;
        }

        if (numero2 == 0) {
          setState(() {
            resultado = 'No se puede dividir entre cero';
          });
        } else {
          setState(() {
            resultado = 'Resultado: ${numero1 / numero2}';
          });
        }
        break;

      case 'potencia':
        if (numero2 == null) {
          setState(() {
            resultado = 'Ingrese un segundo número válido';
          });
          return;
        }
        setState(() {
          resultado = 'Resultado: ${pow(numero1, numero2)}';
        });
        break;

      case 'raiz':
        if (numero1 < 0) {
          //setState(() {
            resultado = 'No se puede calcular raíz de un número negativo';
          //});
        } else {
          setState(() {
            resultado = 'Resultado: ${sqrt(numero1)}';
          });
        }
        break;
    }
  }

  void limpiar() {
    numero1Controller.clear();
    numero2Controller.clear();

    setState(() {
      resultado = 'Resultado:';
    });
  }

  @override
  Widget build(BuildContext context) {
    return FScaffold(
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Center(
          child: ConstrainedBox(
            constraints: const BoxConstraints(maxWidth: 430),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                const Icon(
                  Icons.calculate,
                  size: 80,
                  color: Colors.blue,
                ),

                const SizedBox(height: 15),

                const Text(
                  'Calculadora Básica',
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 26,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 8),

                const Text(
                  'Realiza suma, resta, multiplicación, división, potencia y raíz cuadrada.',
                  textAlign: TextAlign.center,
                  style: TextStyle(fontSize: 14),
                ),

                const SizedBox(height: 25),

                FTextField(
                  control: FTextFieldControl.managed(controller: numero1Controller),                  label: const Text('Primer número'),
                  hint: 'Ingrese el primer número',
                  keyboardType: TextInputType.number,
                ),

                const SizedBox(height: 15),

                FTextField(
                  control: FTextFieldControl.managed(controller: numero2Controller),                  label: const Text('Segundo número'),
                  hint: 'Ingrese el segundo número',
                  keyboardType: TextInputType.number,
                ),

                const SizedBox(height: 25),

                Wrap(
                  spacing: 10,
                  runSpacing: 10,
                  alignment: WrapAlignment.center,
                  children: [
                    FButton(
                      onPress: () => calcular('suma'),
                      child: const Text('Suma +'),
                    ),
                    FButton(
                      onPress: () => calcular('resta'),
                      child: const Text('Resta -'),
                    ),
                    FButton(
                      onPress: () => calcular('multiplicacion'),
                      child: const Text('Multiplicar ×'),
                    ),
                    FButton(
                      onPress: () => calcular('division'),
                      child: const Text('Dividir ÷'),
                    ),
                    FButton(
                      onPress: () => calcular('potencia'),
                      child: const Text('Potencia'),
                    ),
                    FButton(
                      onPress: () => calcular('raiz'),
                      child: const Text('Raíz √'),
                    ),
                  ],
                ),

                const SizedBox(height: 25),

                Container(
                  padding: const EdgeInsets.all(18),
                  decoration: BoxDecoration(
                    color: Colors.blue.shade50,
                    borderRadius: BorderRadius.circular(14),
                    border: Border.all(color: Colors.blue.shade100),
                  ),
                  child: Text(
                    resultado,
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                      fontSize: 21,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),

                const SizedBox(height: 20),

                FButton(
                  onPress: limpiar,
                  child: const Text('Limpiar'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
