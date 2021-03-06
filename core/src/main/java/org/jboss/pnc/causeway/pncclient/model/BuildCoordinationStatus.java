
/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.causeway.pncclient.model;


/**
 * Represents the status of BuildTask in the coordinator.
 * Status is used in dependency resolution and external status update notification.
 * It is not mean to be used as status of DB entity.
 *
 * Created by <a href="mailto:matejonnet@gmail.com">Matej Lazar</a> on 2014-12-22.
 */
@Deprecated
public enum BuildCoordinationStatus {

    NEW,
    ENQUEUED,
    WAITING_FOR_DEPENDENCIES,
    BUILDING,
    BUILD_COMPLETED,
    /** Last build status which is set
     *  after storing to db and
     *  just before dropping from list of running builds.
     *  Used to signal via callback that the build is going to be dropped from queue.
     */
    DONE(true),
    /**
     * Missing configuration, un-satisfied dependencies.
     * Rejected can be set before adding to the list of running builds or before dropping form list of running builds.
     */
    REJECTED(true, true),
    /**
     * Rejected due to failed dependencies.
     */
    REJECTED_FAILED_DEPENDENCIES(true, true),
    /**
     * Rejected because given org.jboss.pnc.model.BuildConfiguration has been already built.
     */
    REJECTED_ALREADY_BUILT(true, false),
    SYSTEM_ERROR(true, true),
    DONE_WITH_ERRORS(true, true),
    CANCELLED(true, true);

    private boolean isFinal;

    private boolean hasFailed = false;

    BuildCoordinationStatus() {
        isFinal = false;
    }

    BuildCoordinationStatus(boolean isFinal) {
        this.isFinal = isFinal;
    }

    BuildCoordinationStatus(boolean isFinal, boolean hasFailed) {
        this.isFinal = isFinal;
        this.hasFailed = hasFailed;
    }

    public boolean isCompleted() {
        return this.isFinal;
    }

    public boolean hasFailed() {
        return this.hasFailed;
    }

}
