/*******************************************************************************
 * Copyright 2016 Christopher Rucinski
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package io.github.ciscorucinski.nanodegree.android.capstone;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressWarnings("UnusedDeclaration")
public class CollectionCard extends CardView
		implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

	private static final int INVALID = -1;
	private static final int DRAWABLE = 0;
	private static final int BITMAP = 1;
	private static final int RESOURCE_ID = 2;
	private static final int URI = 3;
	private final ViewGroup root;
	private ImageView imgImage;
	private TextView txtName;
	private TextView txtVideoCount;
	private TextView txtDescription;
	private View viewOverflow;
	private Button btnAction;
	private String title;
	private int videoCount;
	private String description;
	private String videoCountString;
	private OnClickListener cardClickListener;
	private OnClickListener actionClickListener;
	private PopupMenu.OnMenuItemClickListener popupItemClickListener;
	private int popupMenu;
	private String actionText;
	private @ImageType int currentImageType = INVALID;
	private Object[] image = new Object[] {null, null, null, null};

	public CollectionCard(Context context) {

		this(context, null, 0);
	}


	public CollectionCard(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);

		root = (ViewGroup) LayoutInflater.from(context).inflate(
				R.layout.collection_card_item_internal, this, true);
		init(context, attrs, defStyle);

	}

	public CollectionCard(Context context, AttributeSet attrs) {

		this(context, attrs, 0);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {

		// Load attributes
		final TypedArray a = getContext().obtainStyledAttributes(
				attrs, R.styleable.CollectionCard, defStyle, 0);

		if (a.hasValue(R.styleable.CollectionCard_popup_menu)) {
			popupMenu = a.getResourceId(R.styleable.CollectionCard_popup_menu, 0);
		}

		if (a.hasValue(R.styleable.CollectionCard_actionText)) {
			actionText = a.getString(R.styleable.CollectionCard_actionText);
		} else {
			actionText = "Play";
		}

		a.recycle();

		imgImage = (ImageView) root.findViewById(R.id.card_image);
		txtName = (TextView) root.findViewById(R.id.card_title);
		txtVideoCount = (TextView) root.findViewById(R.id.card_subtitle);
		txtDescription = (TextView) root.findViewById(R.id.card_description);
		btnAction = (Button) root.findViewById(R.id.card_action_button);
		viewOverflow = root.findViewById(R.id.card_overflow_button);

		this.viewOverflow.setOnClickListener(this);
		this.root.setOnClickListener(this);
		this.btnAction.setOnClickListener(this);

		invalidate();

	}

	@Override
	public void invalidate() {

		super.invalidate();

		switch (this.currentImageType) {

			case INVALID:
				this.imgImage.setBackgroundColor(getAccentColor());
				break;
			case DRAWABLE:
				this.imgImage.setImageDrawable((Drawable) getCurrentImage());
				break;
			case BITMAP:
				this.imgImage.setImageBitmap((Bitmap) getCurrentImage());
				break;
			case RESOURCE_ID:
				this.imgImage.setImageResource((int) getCurrentImage());
				break;
			case URI:
				this.imgImage.setImageURI((Uri) getCurrentImage());
				break;

		}

		this.txtName.setText(this.title);
		this.txtVideoCount.setText(this.videoCountString);
		this.txtDescription.setText(this.description);
		this.btnAction.setText(this.actionText);

	}

	private int getAccentColor() {

		TypedValue typedValue = new TypedValue();
		int[] accentColor = new int[] {R.attr.colorAccent};

		TypedArray a = getContext().obtainStyledAttributes(typedValue.data, accentColor);
		int color = a.getColor(0, 0);

		a.recycle();

		return color;

	}

	private Object getCurrentImage() {

		return this.image[this.currentImageType];

	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
		invalidate();

	}

	public void setImage(Bitmap image) { setImageInternal(image, BITMAP); }

	private void setImageInternal(Object image, @ImageType int imageType) {

		this.image[imageType] = image;
		this.currentImageType = imageType;

		invalidate();

	}

	public void setImage(Drawable image) { setImageInternal(image, DRAWABLE); }

	public void setImage(@DrawableRes int image) { setImageInternal(image, RESOURCE_ID); }

	public void setImage(Uri image) { setImageInternal(image, URI); }

	public void setImageColor(@ColorRes int image) { setImageInternal(image, RESOURCE_ID); }

	public int getVideoCount() {

		return videoCount;
	}

	public void setVideoCount(int videoCount) {

		this.videoCount = videoCount;

		this.videoCountString = getContext().getResources()
				.getQuantityString(R.plurals.number_of_videos, videoCount, videoCount);

		invalidate();

	}

	public String getVideoCountString() {

		return videoCountString;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(@NonNull String description) {

		this.description = description;

		if (description.trim().equals("")) txtDescription.setVisibility(GONE);
		else txtDescription.setVisibility(VISIBLE);

		invalidate();

	}

	public void setCardPopup(@MenuRes int menuID) {

		popupMenu = menuID;
	}

	public void setActionText(@NonNull String text) {

		actionText = text;
	}

	public void setActionText(@StringRes int text) {

		actionText = getResources().getString(text);
	}

	public void showPopupMenu(View view) {

		if (popupMenu == 0) return;

		PopupMenu popup = new PopupMenu(getContext(), view);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(popupMenu, popup.getMenu());

	}

	public void setCardClickListener(@NonNull OnClickListener listener) {

		this.cardClickListener = listener;
	}

	public void setActionClickListener(@NonNull OnClickListener listener) {

		this.actionClickListener = listener;
	}

	public void setMenuItemClickListener(@NonNull PopupMenu.OnMenuItemClickListener listener) {

		this.popupItemClickListener = listener;
	}

	public void removeCardClickListener() {

		this.cardClickListener = null;
	}

	public void removeActionClickListener() {

		this.actionClickListener = null;
	}

	public void removeMenuItemClickListener() {

		this.popupItemClickListener = null;
	}

	public void removeAllClickListeners() {

		this.cardClickListener = null;
		this.actionClickListener = null;
		this.popupItemClickListener = null;

	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {

		return false;
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.card_overflow_button && popupMenu != 0) {

			PopupMenu popup = new PopupMenu(getContext(), viewOverflow);
			MenuInflater inflater = popup.getMenuInflater();
			inflater.inflate(popupMenu, popup.getMenu());

			popup.setOnMenuItemClickListener(this.popupItemClickListener);

			popup.show();

		} else {

			Toast.makeText(getContext(),
			               "You have to specify the OnClickListener in your code! " + v.getId(),
			               Toast.LENGTH_SHORT).show();

		}

	}

	@Retention(RetentionPolicy.SOURCE)
	@IntDef({INVALID, DRAWABLE, BITMAP, RESOURCE_ID, URI})
	public @interface ImageType {}
}
