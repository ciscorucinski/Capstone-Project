
/*******************************************************************************
 * Copyright 2016 Christopher Rucinski
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package io.github.ciscorucinski.nanodegree.android.capstone.model;

import io.github.ciscorucinski.nanodegree.android.capstone.model.thumbnail.Default;
import io.github.ciscorucinski.nanodegree.android.capstone.model.thumbnail.High;
import io.github.ciscorucinski.nanodegree.android.capstone.model.thumbnail.Maxres;
import io.github.ciscorucinski.nanodegree.android.capstone.model.thumbnail.Medium;
import io.github.ciscorucinski.nanodegree.android.capstone.model.thumbnail.Standard;

public class Thumbnails {

    private Default _default;
    private Medium medium;
    private High high;
    private Standard standard;
    private Maxres maxres;

    /**
     *
     * @return
     *     The _default
     */
    public Default getDefault() {
        return _default;
    }

    /**
     *
     * @param _default
     *     The default
     */
    public void setDefault(Default _default) {
        this._default = _default;
    }

    /**
     *
     * @return
     *     The medium
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     *
     * @param medium
     *     The medium
     */
    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    /**
     *
     * @return
     *     The high
     */
    public High getHigh() {
        return high;
    }

    /**
     *
     * @param high
     *     The high
     */
    public void setHigh(High high) {
        this.high = high;
    }

    /**
     *
     * @return
     *     The standard
     */
    public Standard getStandard() {
        return standard;
    }

    /**
     *
     * @param standard
     *     The standard
     */
    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    /**
     *
     * @return
     *     The maxres
     */
    public Maxres getMaxres() {
        return maxres;
    }

    /**
     *
     * @param maxres
     *     The maxres
     */
    public void setMaxres(Maxres maxres) {
        this.maxres = maxres;
    }

}
