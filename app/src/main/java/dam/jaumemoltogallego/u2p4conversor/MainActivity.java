package dam.jaumemoltogallego.u2p4conversor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Boolean b = true;

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
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setUI() {
        EditText etPulgada = findViewById(R.id.et_pulgada);
        TextView etResultado = findViewById(R.id.et_resultado);
        Button buttonConvertir = findViewById(R.id.button_Convertir);
        Chip incm = findViewById(R.id.in_cm);
        //TODO Añadimos la referencia al TextView
        TextView textView = findViewById(R.id.textView);
        //TODO Añadimos la referencia al TextView de error
        TextView errorText = findViewById(R.id.errorText);


        //TODO Cambiamos el valor del boolean para saber si estamos de una forma u otra y actualizamos los textos
        incm.setOnClickListener(v -> {
            Log.i(DEBUG_TAG, "Botón in-cm pulsado");
            b = !b;
            actualizarTexto(textView,incm, etPulgada);
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
    private void actualizarTexto(TextView textView, Chip incm , EditText etPulgada)  {
        if (b) {
            textView.setText("Conversión de Centímetros a Pulgadas");
            etPulgada.setText("Introduce los centímetros");
            incm.setText("cm-in");
            incm.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            textView.setText("Conversión de Pulgadas a Centímetros");
            etPulgada.setText("Introduce las pulgadas");
            incm.setText("in-cm");
            incm.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
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
        if (b) {
            return convertirAPulgadas(medida);
        } else {
            return convertirACentimetros(medida);
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
}