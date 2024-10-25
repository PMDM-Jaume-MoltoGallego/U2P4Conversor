package dam.jaumemoltogallego.u2p4conversor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
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


    private void setUI() {
        EditText etPulgada = findViewById(R.id.et_pulgada);
        TextView etResultado = findViewById(R.id.et_resultado);
        Button buttonConvertir = findViewById(R.id.button_Convertir);
        Chip incm = findViewById(R.id.in_cm);
        //TODO Añadimos la referencia al TextView
        TextView textView = findViewById(R.id.textView);


        //TODO Cambiamos el valor del boolean para saber si estamos de una forma u otra y actualizamos los textos
        incm.setOnClickListener(v -> {
            b = !b;
            actualizarTexto(textView,incm);
        });

        //TODO convertimos los datos a pulgadas o centímetros
        buttonConvertir.setOnClickListener(v -> {
            try {
                String dato = etPulgada.getText().toString();
                etResultado.setText(convertirDoble(dato));
            } catch (Exception e) {
                Log.e("LogsConversor", e.getMessage());
            }
        });
    }

    //TODO Actualizamos el texto del título azul y el texto del chip
    private void actualizarTexto(TextView textView, Chip incm) {
        if (b) {
            textView.setText("Conversión de Centímetros a Pulgadas");
            incm.setText("cm-in");
            incm.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            textView.setText("Conversión de Pulgadas a Centímetros");
            incm.setText("in-cm");
            incm.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }

    //TODO Convertimos según sea una conversión u otra
    private String convertirDoble(String medida) {
        //TODO Manejamos la entrada de datos vacía
        if (medida.isEmpty()) {
            return "";
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