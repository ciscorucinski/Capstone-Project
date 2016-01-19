/*******************************************************************************
 * Copyright 2016 Christopher Rucinski
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 ******************************************************************************/

package io.github.ciscorucinski.nanodegree.android.capstone;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PlaylistsRecyclerAdapter
		extends RecyclerView.Adapter<PlaylistsRecyclerAdapter.CollectionHolder> {

	private final List<Object> collections;
	private final Context context;

	public PlaylistsRecyclerAdapter(Context context, List<Object> collections) {

		this.context = context;
		this.collections = collections;

		collections.add(new Object());
		collections.add(new Object());
		collections.add(new Object());
		collections.add(new Object());

	}

	@Override
	public PlaylistsRecyclerAdapter.CollectionHolder onCreateViewHolder(ViewGroup parent,
	                                                                    int viewType) {

		final LayoutInflater inflater = LayoutInflater.from(context);
		final View root = inflater.inflate(R.layout.collection_card_item, parent, false);

		return new CollectionHolder(root);

	}

	@Override
	public void onBindViewHolder(CollectionHolder holder, int position) {

		holder.card.setTitle("Card " + position);

		Drawable image_id;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			image_id = context.getDrawable(R.drawable.test_image);
		} else {
			image_id = context.getResources().getDrawable(R.drawable.test_image);
		}

		switch (position) {

			case 0:
				holder.card.setDescription("");
				holder.card.setImage(image_id);
				break;
			case 1:
				holder.card.setDescription("Description");
				break;
			case 2:
				holder.card.setDescription("");
				break;
			case 3:
				holder.card.setDescription("Description \n\n\n\n\n\n\n Unseeable!!");
				break;

		}

		holder.card.setVideoCount(position);

	}

	@Override
	public int getItemCount() {

		return collections.size();
	}

	public class CollectionHolder extends RecyclerView.ViewHolder {

		private final CollectionCard card;

		public CollectionHolder(final View root) {

			super(root);

			card = (CollectionCard) root.findViewById(R.id.collection_card);

		}

	}
}