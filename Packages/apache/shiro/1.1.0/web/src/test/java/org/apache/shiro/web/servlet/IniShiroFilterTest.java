/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.shiro.web.servlet;

import static org.easymock.EasyMock.*;
import org.junit.Test;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;


/**
 * Tests for {@link IniShiroFilter}.
 *
 * @since 0.9
 */
public class IniShiroFilterTest {

    @SuppressWarnings({"FieldCanBeLocal"})
    private IniShiroFilter filter;
    private FilterConfig mockFilterConfig;
    private ServletContext mockServletContext;

    protected void setUp(String config) {
        mockFilterConfig = createMock(FilterConfig.class);
        mockServletContext = createMock(ServletContext.class);

        expect(mockFilterConfig.getServletContext()).andReturn(mockServletContext).anyTimes();
        expect(mockFilterConfig.getInitParameter(IniShiroFilter.CONFIG_INIT_PARAM_NAME)).andReturn(config).once();
        expect(mockFilterConfig.getInitParameter(IniShiroFilter.CONFIG_PATH_INIT_PARAM_NAME)).andReturn(null).once();
    }

    protected void setUpWithPathConfig(String path) {
        mockFilterConfig = createMock(FilterConfig.class);
        mockServletContext = createMock(ServletContext.class);

        expect(mockFilterConfig.getServletContext()).andReturn(mockServletContext).anyTimes();
        expect(mockFilterConfig.getInitParameter(IniShiroFilter.CONFIG_INIT_PARAM_NAME)).andReturn(null).anyTimes();
        expect(mockFilterConfig.getInitParameter(IniShiroFilter.CONFIG_PATH_INIT_PARAM_NAME)).andReturn(path).anyTimes();
    }

    public void tearDown() throws Exception {
        reset(mockServletContext);
        reset(mockFilterConfig);

        replay(mockServletContext);

        this.filter.destroy();

        verify(mockServletContext);
        verify(mockFilterConfig);
    }

    protected void replayAndVerify() throws Exception {
        replay(mockServletContext);
        replay(mockFilterConfig);

        this.filter = new IniShiroFilter();
        this.filter.init(mockFilterConfig);

        verify(mockFilterConfig);
        verify(mockServletContext);
    }


    @Test
    public void testDefaultConfig() throws Exception {
        setUp(null);
        replayAndVerify();
    }

    @Test
    public void testSimpleConfig() throws Exception {
        setUp("[filters]\nauthc.successUrl = /index.jsp");
        replayAndVerify();
    }

    @Test
    public void testSimplePathConfig() throws Exception {
        setUpWithPathConfig("classpath:IniShiroFilterTest.ini");
        replayAndVerify();
    }
}
