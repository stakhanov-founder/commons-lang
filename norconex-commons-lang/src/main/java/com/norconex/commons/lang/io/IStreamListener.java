/* Copyright 2010-2017 Norconex Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.norconex.commons.lang.io;

/**
 * Listener that is being notified every time a line is processed from a 
 * given stream.
 * @author Pascal Essiembre
 * @see StreamGobbler
 * @deprecated As of 1.13.0, use {@link IInputStreamListener} instead.
 */
@Deprecated
public interface IStreamListener {
    
    /**
     * Invoked when a line is streamed.
     * @param type type of line, as defined by the class using the listener
     * @param line line processed
     */
    void lineStreamed(String type, String line);
}
