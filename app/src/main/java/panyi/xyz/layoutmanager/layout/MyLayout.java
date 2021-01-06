package panyi.xyz.layoutmanager.layout;

import androidx.recyclerview.widget.RecyclerView;

public class MyLayout extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT , RecyclerView.LayoutParams.WRAP_CONTENT);
    }
}
