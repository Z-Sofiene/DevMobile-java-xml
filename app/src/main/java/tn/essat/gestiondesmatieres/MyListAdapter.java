package tn.essat.gestiondesmatieres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends BaseAdapter {

    private Context context;
    private List<Matiere> liste;

    public MyListAdapter(Context context, List<Matiere> liste) {
        this.context = context;
        this.liste = liste;
    }

    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return liste.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup vg) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_list_layout, vg, false);
        }
        Matiere m=liste.get(position);
        TextView matiere=convertView.findViewById(R.id.textTitre);
        TextView classe=convertView.findViewById(R.id.textNiveau);

        matiere.setText(m.getTitre());
        classe.setText(m.getNiveau());
        return convertView;
    }
}