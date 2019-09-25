package com.zhyen.android.picture_selected.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhyen.android.R;
import com.zhyen.android.picture_selected.SelectionSpec;
import com.zhyen.android.picture_selected.entity.Item;
import com.zhyen.android.picture_selected.listener.OnFragmentInteractionListener;
import com.zhyen.android.picture_selected.util.PhotoMetadataUtils;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class PreviewItemFragment extends Fragment {

    private static final String TAG = "PreviewItemFragment";

    private static final String ARGS_ITEM = "args_item";
    private OnFragmentInteractionListener mListener;

    public static PreviewItemFragment newInstance(Item item) {
        PreviewItemFragment fragment = new PreviewItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_ITEM, item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_preview, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Item item = getArguments().getParcelable(ARGS_ITEM);
        if (item == null) {
            return;
        }
        View videoPlayButton = view.findViewById(R.id.video_play_button);
        if (item.isVideo()) {
            videoPlayButton.setVisibility(View.VISIBLE);
            videoPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(item.uri, "video/*");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), R.string.error_no_video_activity, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            videoPlayButton.setVisibility(View.GONE);
        }

        ImageViewTouch image = (ImageViewTouch) view.findViewById(R.id.image_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        image.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
            @Override
            public void onSingleTapConfirmed() {
                if (mListener != null) {
                    mListener.onClick();
                }
            }
        });

        Point size = PhotoMetadataUtils.getBitmapSize(item.getContentUri(), getActivity());
        if (item.isGif()) {
            SelectionSpec.getInstance().imageEngine.loadGifImage(getContext(), size.x, size.y, image,
                    item.getContentUri());
        } else {
            SelectionSpec.getInstance().imageEngine.loadImage(getContext(), size.x, size.y, image,
                    item.getContentUri());
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void resetView() {
        if (getView() != null) {
            ((ImageViewTouch) getView().findViewById(R.id.image_view)).resetMatrix();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
