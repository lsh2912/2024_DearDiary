package com.example.mydiaryapp.Calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mydiaryapp.R;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private List<Calendar> calendarList;
    private Context context;
    public CalendarAdapter(List<Calendar> calendarList) {
        this.calendarList = calendarList;
    }

    @Override
    public long getItemId(int position) {
        return calendarList.get(position).getId();
    }

    @Override
    public int getItemCount() { return calendarList.size(); }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_calendar, parent, false);
        return new CalendarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        Calendar calendar = calendarList.get(position);

        //캘린더 이름 설정
        holder.calendarName.setText(calendar.getCalendarName());

        // 체크박스 상태 설정
        holder.calendarCheckBox.setChecked(calendar.isSelected());
        holder.calendarCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            calendar.setSelected(isChecked); // 선택 상태 업데이트
        });

        // 수정 아이콘 클릭 시 처리
        holder.editIcon.setOnClickListener(v -> {
            // 수정 프래그먼트 호출
            CalendarEditFragment editCalendarFragment = CalendarEditFragment.newInstance(calendar.getId());
            ((CalendarActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_calendar, editCalendarFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // 삭제 아이콘 클릭 시 처리
        holder.deleteIcon.setOnClickListener(v -> {
            // 삭제 처리
            deleteCalendar(calendar);
        });
    }


    // 캘린더 삭제 처리
    private void deleteCalendar(Calendar calendar) {
        // DB에서 삭제 로직 추가
        CalendarDatabaseHelper dbHelper = new CalendarDatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(CalendarDatabaseHelper.TABLE_CALENDARS,
                CalendarDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(calendar.getId())});

        // 리소스 해제
        if (db.isOpen()) {
            db.close();
        }
        // 목록에서 삭제
        calendarList.remove(calendar);
        notifyDataSetChanged();

        // 사용자에게 안내 메시지
        Toast.makeText(context, "캘린더가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView calendarName;
        CheckBox calendarCheckBox;
        ImageView editIcon, deleteIcon;

        CalendarViewHolder(View itemView) {
            super(itemView);
            calendarCheckBox = itemView.findViewById(R.id.calendarCheckBox);
            calendarName = itemView.findViewById(R.id.calendarName);
            editIcon = itemView.findViewById(R.id.editIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }
}