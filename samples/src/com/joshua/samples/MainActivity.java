package com.joshua.samples;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static List<Item> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        ListView listView = findViewById(R.id.list);
        CustomAdapter adapter = new CustomAdapter(MainActivity.this,
                R.layout.item_main_list, mData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, mData.get(position).clazz));
            }
        });
    }

        private void initData() {
            mData.add(new Item("新效果", null, NewEffectActivity.class));
        }

        class CustomAdapter extends ArrayAdapter {
            private LayoutInflater mInflater;
            private int mResource;

            public CustomAdapter(Context context, int resource, List<Item> objects) {
                super(context, resource, objects);
                mInflater = LayoutInflater.from(context);
                mResource = resource;
            }

            @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item = (Item) getItem(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(mResource, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.setData(item);
            return convertView;
        }
    }

    class ViewHolder {
        private TextView title;
        private TextView description;

        public ViewHolder(View view) {
            title = view.findViewById(R.id.item_title);
            description = view.findViewById(R.id.item_content);
        }

        public void setData(Item item) {
            title.setText(item.title);
        }
    }

    class Item {
        final String title;
        final String description;
        final Class<? extends Activity> clazz;

        public Item(String title, String description, Class<? extends Activity> clazz) {
            this.title = title;
            this.description = description;
            this.clazz = clazz;
        }
    }
}
