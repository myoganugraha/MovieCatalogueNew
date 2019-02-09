package itk.myoganugraha.moviecataloguenew.Util;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.ButterKnife;
import itk.myoganugraha.moviecataloguenew.Activity.SettingActivity;
import itk.myoganugraha.moviecataloguenew.R;
import itk.myoganugraha.moviecataloguenew.Util.Upcoming.ScheduleTask;

public class NewPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener{

    private AlarmReceiver alarmReceiver = new AlarmReceiver();
    private ScheduleTask scheduleTask;

    @BindString(R.string.key_reminder_daily)
    String reminderDaily;

    @BindString(R.string.key_reminder_upcoming)
    String reminderUpcoming;

    @Override
    public void onCreate(final Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        addPreferencesFromResource(R.xml.preference);

        ButterKnife.bind(this, getActivity());

        findPreference(reminderDaily).setOnPreferenceChangeListener(this);
        findPreference(reminderUpcoming).setOnPreferenceChangeListener(this);

        scheduleTask = new ScheduleTask(getActivity());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();
        boolean isSelected = (boolean) o;

        if(key.equals(reminderDaily)){
            if(isSelected){
                alarmReceiver.setRepeatingAlarm(getActivity(), alarmReceiver.REPEATING_TYPE, "07:00", getString(R.string.label_alarm_daily_reminder));
            } else {
                alarmReceiver.cancelAlarm(getActivity(), alarmReceiver.REPEATING_TYPE);
            }
            Toast.makeText(getActivity(), getString(R.string.label_daily_reminder) + " " + (isSelected ? "Activated" : "Deactivated"), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (key.equals(reminderUpcoming)){
            if(isSelected){
                scheduleTask.createPeriodicTask();
            } else scheduleTask.cancelPeriodicTask();

            Toast.makeText(getActivity(), getString(R.string.label_upcoming_reminder) + " " + (isSelected ? "Activated" : "Deactivated"), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}
