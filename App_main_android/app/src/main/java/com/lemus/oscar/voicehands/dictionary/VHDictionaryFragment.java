package com.lemus.oscar.voicehands.dictionary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lemus.oscar.voicehands.R;
import com.lemus.oscar.voicehands.VHDataBaseHelper;
import com.lemus.oscar.voicehands.dictionary.word.WordContent;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VHDictionaryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VHDictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VHDictionaryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Own Content
     */

    private RecyclerView recyclerView;

    public VHDictionaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VHDictionaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VHDictionaryFragment newInstance(String param1, String param2) {
        VHDictionaryFragment fragment = new VHDictionaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vhdictionary, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_dictionary);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Category> categories = new ArrayList<>();

        SQLiteDatabase bd = SQLiteDatabase.openDatabase(VHDataBaseHelper.dbPath, null, 1);
        Cursor fila = bd.rawQuery("SELECT * FROM category", null);
        fila.moveToFirst();
        do {
            categories.add(new Category(fila.getString(1), fila.getString(2)));
        } while (fila.moveToNext());

        bd.close();

        CategoryAdapter adapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerCategoryClickListener(getContext(), recyclerView, new RecyclerCategoryClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "Sucedió" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WordListActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("category_id", position+1);
                intent.putExtras(extras);
                WordContent.ITEMS.clear();
                WordContent.ITEM_MAP.clear();
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
        private ArrayList<Category> categories;

        public CategoryAdapter(ArrayList<Category> categories) {
            this.categories = categories;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view, parent, false);
            CategoryHolder ctyh = new CategoryHolder(v);
            return ctyh;
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            holder.nameCategory.setText(categories.get(position).getCategory());
            ByteArrayInputStream imageStream;
            Bitmap theImage;

            SQLiteDatabase bd = SQLiteDatabase.openDatabase(VHDataBaseHelper.dbPath, null, 1);
            Cursor fila = bd.rawQuery("SELECT * FROM images where code = '" + categories.get(position).getImageCategory() + "'", null);
            fila.moveToFirst();
            do {
                imageStream = new ByteArrayInputStream(fila.getBlob(1));
                theImage = BitmapFactory.decodeStream(imageStream);
            } while (fila.moveToNext());

            bd.close();

            holder.imageCategory.setImageBitmap(theImage);
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder{

        CardView cv;
        ImageView imageCategory;
        TextView nameCategory;

        CategoryHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_dictionary);
            imageCategory = (ImageView) itemView.findViewById(R.id.ivCV_dictionary);
            nameCategory = (TextView) itemView.findViewById(R.id.tvCV_dictionary);
        }

    }

    public class Category {
        private String category;
        private String imageCategory;

        public Category(String category, String imageCategory) {
            this.category = category;
            this.imageCategory = imageCategory;
        }

        public String getCategory() {
            return category;
        }

        public String getImageCategory() {
            return imageCategory;
        }
    }
}
