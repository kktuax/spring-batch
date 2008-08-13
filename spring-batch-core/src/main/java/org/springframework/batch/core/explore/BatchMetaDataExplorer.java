/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.core.explore;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;

/**
 * @author Dave Syer
 * 
 */
public interface BatchMetaDataExplorer {

	/**
	 * @param jobName the name of the job to query
	 * @param count the maximum number of instances to return
	 * @return the latest {@link JobInstance} values up to a maximum of count values
	 */
	List<JobInstance> getLastJobInstances(String jobName, int count);

	/**
	 * @param jobName the name of the job
	 * @param jobParameters the parameters to match
	 * @return true if a {@link JobInstance} already exists for this job name and job parameters
	 */
	boolean isJobInstanceExists(String jobName, JobParameters jobParameters);

	/**
	 * @param executionId
	 * @return the {@link JobExecution} with this id, or null
	 */
	JobExecution getJobExecution(Long executionId);

}