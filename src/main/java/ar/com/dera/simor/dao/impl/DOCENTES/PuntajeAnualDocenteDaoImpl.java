/*******************************************************************************
 * Copyright (c) 2014 Gaspar Rajoy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Gaspar Rajoy - initial API and implementation
 ******************************************************************************/
package ar.com.dera.simor.dao.impl.DOCENTES;

import org.springframework.stereotype.Repository;

import ar.com.dera.simor.common.entities.DOCENTES.PuntajeAnualDocente;
import ar.com.dera.simor.dao.impl.GenericMongoDAOImpl;

@Repository("puntajeAnualDocenteDao")
public class PuntajeAnualDocenteDaoImpl extends GenericMongoDAOImpl<PuntajeAnualDocente>{
	
	public PuntajeAnualDocenteDaoImpl() {
		super(PuntajeAnualDocente.class);
	}
}
