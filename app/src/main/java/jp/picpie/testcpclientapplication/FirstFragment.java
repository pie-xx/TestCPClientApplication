package jp.picpie.testcpclientapplication;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.sql.Blob;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = getActivity().getContentResolver().query(Uri.parse("content://cptest.picpie.jp/aaa"), null, null, null, null);

                TextView  tv = getActivity().findViewById(R.id.textview_first);
                StringBuffer rtn = new StringBuffer();
                while (c.moveToNext()) {
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        if( c.getType(i)==Cursor.FIELD_TYPE_BLOB){
                            rtn.append(c.getColumnName(i));
                            rtn.append("=");
                            byte[] blob = c.getBlob(i);
                            rtn.append("blob size "+String.valueOf(blob.length));
                            for(int n=0; n < blob.length; ++n ){
                                rtn.append(" ");
                                rtn.append(String.valueOf(blob[n]));
                            }
                            rtn.append("\n");
                            Log.d(getClass().getSimpleName(), Integer.toString(i) + ": " + c.getColumnName(i) );
                        }else {
                            rtn.append(c.getColumnName(i));
                            rtn.append("=");
                            rtn.append(c.getString(i));
                            rtn.append(" (Int " + String.valueOf(c.getInt(i)) + ")");
                            rtn.append(" (Long " + String.valueOf(c.getLong(i)) + ")");
                            rtn.append(" (float " + String.valueOf(c.getFloat(i)) + ")");
                            if( c.getType(i)!=Cursor.FIELD_TYPE_INTEGER && c.getType(i)!=Cursor.FIELD_TYPE_FLOAT) {
                                byte[] blob = c.getBlob(i);
                                if( blob == null ) {
                                    rtn.append("blob == null " );
                                }else{
                                    rtn.append("blob size " + String.valueOf(blob.length));
                                    for (int n = 0; n < blob.length; ++n) {
                                        rtn.append(" ");
                                        rtn.append(String.valueOf(blob[n]));
                                    }
                                }
                            }
                            rtn.append("\n");
                            Log.d(getClass().getSimpleName(), Integer.toString(i) + ": " + c.getColumnName(i) + " = " + c.getString(i));
                        }
                    }
                }
                tv.setText(rtn.toString());
                /*
                  //NavHostFragment.findNavController(FirstFragment.this)
                //        .navigate(R.id.action_FirstFragment_to_SecondFragment);

                String[] headers = new String[]{"_id", "Float", "String",  "Null"};
                int[] layouts = new int[]{R.id.id, R.id.first, R.id.second, R.id.third};
                ListView lv = (ListView)getActivity().findViewById(R.id.CPLIST);
                SimpleCursorAdapter adapter = new SimpleCursorAdapter( getActivity(), R.layout.cptable_layout, c, headers, layouts, 0);
                lv.setAdapter(adapter);        // Inflate the layout for this fragment
                 */
            }
        });
    }
}