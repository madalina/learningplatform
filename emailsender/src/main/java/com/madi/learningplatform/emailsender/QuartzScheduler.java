package com.madi.learningplatform.emailsender;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import java.util.Date;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzScheduler {
    public static void main(String[] args) throws InterruptedException {

        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            Logger.getLogger(QuartzScheduler.class).info(
                    "Started the quartz scheduler at " + new Date());
            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(NoteReminderUpdater.class).withIdentity(
                    "job1", "group1").build();

            Trigger trigger = newTrigger()
                    .withIdentity("trigger_emails", "group_emails").startNow()
                    .withSchedule(dailyAtHourAndMinute(7, 0)).build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
