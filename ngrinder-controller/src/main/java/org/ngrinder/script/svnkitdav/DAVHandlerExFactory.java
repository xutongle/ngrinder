/*
 * Copyright (C) 2012 - 2012 NHN Corporation
 * All rights reserved.
 *
 * This file is part of The nGrinder software distribution. Refer to
 * the file LICENSE which is part of The nGrinder distribution for
 * licensing details. The nGrinder distribution is available on the
 * Internet at http://nhnopensource.org/ngrinder
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ngrinder.script.svnkitdav;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.internal.server.dav.DAVRepositoryManager;
import org.tmatesoft.svn.core.internal.server.dav.handlers.DAVHandlerFactory;
import org.tmatesoft.svn.core.internal.server.dav.handlers.ServletDAVHandler;

/**
 * nGrinder customized version of DAVHandler Factory. It dispatches {@link DAVPropfindExHandler}
 * instead of {@link DAVPropfindHandler} which fixes the bug.
 * 
 * @author JunHo Yoon
 * @since 3.0.4
 */
public final class DAVHandlerExFactory {

	public static final String METHOD_PROPFIND = "PROPFIND";
	
	private DAVHandlerExFactory() {
	}

	/**
	 * Create a servlet DAV handler.
	 * 
	 * @param manager
	 * 			manager
	 * @param request
	 * 			servlet request
	 * @param response
	 * 			servlet response
	 * 
	 * @return handler
	 * 
	 * @throws SVNException
	 * 			method is not PROPFIND
	 */
	public static ServletDAVHandler createHandler(DAVRepositoryManager manager, HttpServletRequest request,
					HttpServletResponse response) throws SVNException {
		String methodName = request.getMethod();

		if (METHOD_PROPFIND.equals(methodName)) {
			return new DAVPropfindExHandler(manager, request, response);
		} else {
			return DAVHandlerFactory.createHandler(manager, request, response);
		}
	}

}