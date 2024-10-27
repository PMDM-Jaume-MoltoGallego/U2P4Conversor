package dam.jaumemoltogallego.u2p4conversor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    private Boolean cm = false;
    private Boolean km = false;
    private Boolean c = false;
    private String caso = "1";
    public EditText etPulgada;
    public TextView etResultado;
    public Button buttonConvertir;
    public Chip incm;
    public Chip kmmi;
    public Chip cf;
    public ImageButton ayuda;
    //TODO Añadimos la referencia al TextView
    public TextView textView;
    //TODO Añadimos la referencia al TextView de error
    public TextView errorText;

    //TODO Creamos donde guardar los estados de las variables
    private static final String KEY_B = "key_b";
    private static final String KEY_KM = "key_km";
    private static final String KEY_C = "key_c";

    private static final String KEY_CASO = "key_caso";

    private static final String KEY_ERROR_TEXT = "key_error_text";
    private static final String KEY_INPUT_VALUE = "key_input_value";
    private static final String KEY_RESULT_VALUE = "key_result_value";
    private static final String KEY_TITULO = "key_titulo";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUI();
    }


    //TODO Sirve para cuando se ejecuta por primera vez la aplicación,
    // cuando ponemos en primer plano nuestra aplicación desde las aplicaciones abiertas
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(DEBUG_TAG,"Volvemos con la APP");
    }

    //TODO Sirve para varias utilidades,como para cuando le damos al boton de atrás,
    // cuando le damos al boton HOME teniendo la aplicación en primer plano,
    // cuando accedemos a las aplicaciones abiertas
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(DEBUG_TAG,"Pausamos la APP");
    }

    //TODO Sirve para cuando el usuario le da al boton de atrás o lo hace el sistema
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(DEBUG_TAG,"Cierra la APP");
    }

    //TODO Sirve para cuando se ejecuta por primera vez la aplicación,
    // cuando ponemos en primer plano nuestra aplicación desde las aplicaciones abiertas
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(DEBUG_TAG,"Empezamos la APP");
    }

    //TODO Sirve para cuando le damos al boton de atrás,
    // cuando le damos al boton HOME teniendo la aplicación en primer plano,
    // cuando accedemos a las aplicaciones abiertas
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(DEBUG_TAG,"Paramos la APP");
    }

    //TODO Sirve para cuando ponemos en primer plano nuestra aplicación desde las aplicaciones abiertas
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(DEBUG_TAG,"Volvemos a utilizar la APP");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        cm = savedInstanceState.getBoolean(KEY_B, false);
        km = savedInstanceState.getBoolean(KEY_KM, false);
        c = savedInstanceState.getBoolean(KEY_C, false);

        caso = savedInstanceState.getString(KEY_CASO,"1");
        etPulgada.setText(savedInstanceState.getString(KEY_INPUT_VALUE, ""));
        etResultado.setText(savedInstanceState.getString(KEY_RESULT_VALUE, ""));
        textView.setText(savedInstanceState.getString(KEY_TITULO));
        String errorMessage = savedInstanceState.getString(KEY_ERROR_TEXT, null);
        if (errorMessage != null) {
            errorText.setText(errorMessage);
            errorText.setVisibility(View.VISIBLE);
        } else {
            errorText.setVisibility(View.GONE);
        }
        Log.i(DEBUG_TAG, "Valor variables restaurado");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_B, cm);
        outState.putBoolean(KEY_KM, km);
        outState.putBoolean(KEY_C, c);

        outState.putString(KEY_CASO, caso);
        outState.putString(KEY_INPUT_VALUE, etPulgada.getText().toString());
        outState.putString(KEY_RESULT_VALUE, etResultado.getText().toString());
        outState.putString(KEY_TITULO, textView.getText().toString());
        if (errorText.getVisibility() == View.VISIBLE) {
            outState.putString(KEY_ERROR_TEXT, errorText.getText().toString());
        }
        Log.i(DEBUG_TAG, "Valor variables guardado");
    }

    private void setUI() {
        etPulgada = findViewById(R.id.et_pulgada);
        etResultado = findViewById(R.id.et_resultado);
        buttonConvertir = findViewById(R.id.button_Convertir);
        incm = findViewById(R.id.in_cm);
        kmmi = findViewById(R.id.km_mi);
        cf = findViewById(R.id.c_f);
        ayuda = findViewById(R.id.ayuda);
        //TODO Añadimos la referencia al TextView
        textView = findViewById(R.id.textView);
        //TODO Añadimos la referencia al TextView de error
        errorText = findViewById(R.id.errorText);


        //TODO Cambiamos el valor del boolean para saber si estamos de una forma u otra y actualizamos los textos
        incm.setOnClickListener(v -> {
            Log.i(DEBUG_TAG, "Botón in-cm pulsado");
            cm = !cm;
            actualizarTexto(textView, incm, etPulgada, etResultado);
        });

        kmmi.setOnClickListener(v -> {
            Log.i(DEBUG_TAG, "Botón km-mi pulsado");
            km = !km;
            actualizarTextoKM(textView, kmmi, etPulgada, etResultado);
        });

        cf.setOnClickListener(v -> {
            Log.i(DEBUG_TAG, "Botón Cº-Fº pulsado");
            c = !c;
            actualizarTextoCF(textView, cf, etPulgada, etResultado);
        });

        //TODO Hacemos que el botón ayuda funcione
        ayuda.setOnClickListener(v -> {
            Log.i(DEBUG_TAG, "Botón ayuda pulsado");

            setContentView(R.layout.activity_ayuda);
        });

        //TODO convertimos los datos a pulgadas o centímetros
        buttonConvertir.setOnClickListener(v -> {
            Log.i(DEBUG_TAG, "Botón Convertir pulsado");
            try {
                String dato = etPulgada.getText().toString();
                etResultado.setText(convertirDoble(dato));
                //TODO Nos asegurames que si funciona correctamente NO se vea el error
                errorText.setVisibility(View.GONE);
            } catch (Exception e) {
                //TODO Si no funciona hacemos que se vea el error con el mensaje apropiado
                errorText.setText(e.getMessage());
                errorText.setVisibility(View.VISIBLE);
                Log.e("LogsConversor", e.getMessage());
            }
        });
    }

    //TODO Actualizamos el texto del título azul y el texto del chip
    private void actualizarTexto(TextView textView, Chip incm , EditText etPulgada, TextView etResultado)  {
        if (cm) {
            textView.setText("Conversión de Centímetros a Pulgadas");
            etPulgada.setText("Introduce los centímetros");
            etResultado.setText("Resultado");
            incm.setText("cm-in");
            incm.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            caso = "2";
        } else {
            textView.setText("Conversión de Pulgadas a Centímetros");
            etPulgada.setText("Introduce las pulgadas");
            etResultado.setText("Resultado");
            incm.setText("in-cm");
            incm.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            caso = "1";
        }
    }

    private void actualizarTextoKM(TextView textView, Chip kmmi , EditText etPulgada, TextView etResultado)  {
        if (km) {
            textView.setText("Conversión de Millas a Kilómetros");
            etPulgada.setText("Introduce las millas");
            etResultado.setText("Resultado");
            kmmi.setText("mi-km");
            kmmi.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            caso = "4";
        } else {
            textView.setText("Conversión de Kilómetros a Millas");
            etPulgada.setText("Introduce los kilómetros");
            etResultado.setText("Resultado");
            kmmi.setText("km-mi");
            kmmi.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            caso = "3";
        }
    }

    private void actualizarTextoCF(TextView textView, Chip cf , EditText etPulgada, TextView etResultado)  {
        if (c) {
            textView.setText("Conversión de Fahrenheit a Celsius");
            etPulgada.setText("Introduce los fahrenheit");
            etResultado.setText("Resultado");
            cf.setText("Fº-Cº");
            cf.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            caso = "8";
        } else {
            textView.setText("Conversión de Celsius a Fahrenheit");
            etPulgada.setText("Introduce los celsius");
            etResultado.setText("Resultado");
            cf.setText("Cº-Fº");
            cf.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            caso = "5";
        }
    }

    //TODO Convertimos según sea una conversión u otra
    private String convertirDoble(String medida) throws Exception {
        //TODO Manejamos la entrada de datos vacía
        if (medida.isEmpty()) {
            throw new Exception("El valor no puede estar vacío");
        }
        if (Double.parseDouble(medida) < 1){
            throw new Exception("Sólo números >=1");
        }
        if (caso.equals("1")) {
            return convertirAPulgadas(medida);
        }if (caso.equals("2")){
            return convertirACentimetros(medida);
        }if (caso.equals("3")) {
            return convertirAMillas(medida);
        }if (caso.equals("4")) {
            return convertirAKilometros(medida);
        }if (caso.equals("5")) {
            return convertirAFahrenheit(medida);
        }else {
            return convertirACelsius(medida);
        }
    }


    //TODO Pulgadas a Centímetros
    private String convertirAPulgadas(String pulgadaText){
        double pulgadaValue = Double.parseDouble(pulgadaText) * 2.54;
        return String.format("%.2f pulgadas",pulgadaValue);
    }

    //TODO Centímetros a pulgadas
    private String convertirACentimetros(String centimetrosText){
        double centimetrosValue = Double.parseDouble(centimetrosText) / 2.54;
        return String.format("%.2f centímetros", centimetrosValue);
    }

    //TODO Kilómetros a millas
    private String convertirAMillas(String kilometrosText) {
        double kilometrosValue = Double.parseDouble(kilometrosText) * 0.621371;
        return String.format("%.2f millas", kilometrosValue);
    }
    //TODO Millas a kilómetros
    private String convertirAKilometros(String millasText) {
        double millasValue = Double.parseDouble(millasText) * 1.60934;
        return String.format("%.2f kilómetros", millasValue);
    }

    //TODO Celsius a Fahrenheit
    private String convertirAFahrenheit(String celsiusText) {
        double celsiusValue = Double.parseDouble(celsiusText);
        double fahrenheitValue = (celsiusValue * 9.0 / 5.0) + 32;
        return String.format("%.2f °F", fahrenheitValue);
    }

    //TODO Fahrenheit a Celsius
    private String convertirACelsius(String fahrenheitText) {
        double fahrenheitValue = Double.parseDouble(fahrenheitText);
        double celsiusValue = (fahrenheitValue - 32) * 5.0 / 9.0;
        return String.format("%.2f °C", celsiusValue);
    }
    //TODO Al hacer el cambio de horientación lo que se hace es un relanzamiento o una recreación de
    // la actividad, esto implica que se tiene que reorganizar toda la vista y se destruyen los TextViews creados
    // y por ello necesitaremos guardar la info con el método onSaveInstanceState y el método onRestoreInstanceState
}