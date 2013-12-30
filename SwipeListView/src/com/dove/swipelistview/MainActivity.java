
package com.dove.swipelistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.dove.swiplistview.R;

public class MainActivity extends Activity {

    HoverCellListView mHoverCellListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHoverCellListView = (HoverCellListView) findViewById(R.id.hover_cell_list_vw);

        ArrayAdapter<String> adatper = new ArrayAdapter<String>(this, R.layout.text_view,
                Cheeses.sCheeseStrings);

        mHoverCellListView.setAdapter(adatper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
