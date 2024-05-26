package com.shipdoc.global.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {
	private final TaskScheduler taskScheduler;
	private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

	public void scheduleTask(String taskId, Runnable task, Date scheduledTime) {
		ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(task, scheduledTime);
		scheduledTasks.put(taskId, scheduledFuture);
	}

	public void cancelTask(String taskId) {
		ScheduledFuture<?> scheduledFuture = scheduledTasks.get(taskId);
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
			scheduledTasks.remove(taskId);
		}
		else{
			log.error("예정된 예약 발송 메세지가 존재하지 않습니다.");
		}
	}
}
