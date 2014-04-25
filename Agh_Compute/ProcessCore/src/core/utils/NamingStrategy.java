package core.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * Naming strategy for table names.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public class NamingStrategy extends ImprovedNamingStrategy {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 6830369528932751123L;
	
	/** Words that can be keywords. */
	private static final Set<String> KEYWORDS;
	
	/**
	 * 
	 * Constructor.
	 */
	public NamingStrategy() { }
	
	static {
		String[] keywords = { 
			"type", "number", "size", "date", "select",
			"insert", "access", "else", "modify", "start", "add",
			"exclusive", "noaudit", "all", "exists", "nocompress",
			"session", "alter", "file", "not", "set", "and", "float",
			"notfound", "share", "any", "for", "nowait", "arraylen",
			"from", "null", "smallint", "as", "grant", "sqlbuf", "asc",
			"group", "of", "successful", "audit", "having", "offline",
			"synonym", "between", "identified", "on", "sysdate", "by",
			"immediate", "online", "table", "char", "in", "option", "then",
			"check", "increment", "or", "to", "cluster", "index", "order",
			"trigger", "column", "initial", "pctfree", "uid", "comment",
			"prior", "union", "compress", "integer", "privileges",
			"unique", "connect", "intersect", "public", "update", "create",
			"into", "raw", "user", "current", "is", "rename", "validate",
			"level", "resource", "values", "decimal", "like", "revoke",
			"varchar", "default", "lock", "row", "varchar2", "delete",
			"long", "rowid", "view", "desc", "maxextents", "rowlabel",
			"whenever", "distinct", "minus", "rownum", "where", "drop",
			"mode", "rows", "with", "admin", "cursor", "found", "mount",
			"after", "cycle", "function", "next", "allocate", "database",
			"go", "new", "analyze", "datafile", "goto", "noarchivelog",
			"archive", "dba", "groups", "nocache", "archivelog", "dec",
			"including", "nocycle", "authorization", "declare",
			"indicator", "nomaxvalue", "avg", "disable", "initrans",
			"nominvalue", "backup", "dismount", "instance", "none",
			"begin", "double", "int", "noorder", "become", "dump", "key",
			"noresetlogs", "before", "each", "language", "normal", "block",
			"enable", "layer", "nosort", "body", "end", "link", "numeric",
			"cache", "escape", "lists", "off", "cancel", "events",
			"logfile", "old", "cascade", "except", "manage", "only",
			"change", "exceptions", "manual", "open", "character", "exec",
			"max", "optimal", "checkpoint", "explain", "maxdatafiles",
			"own", "close", "execute", "maxinstances", "package", "cobol",
			"extent", "maxlogfiles", "parallel", "commit", "externally",
			"maxloghistory", "pctincrease", "compile", "fetch",
			"maxlogmembers", "pctused", "constraint", "flush", "maxtrans",
			"plan", "constraints", "freelist", "maxvalue", "pli",
			"contents", "freelists", "min", "precision", "continue",
			"force", "minextents", "primary", "controlfile", "foreign",
			"minvalue", "private", "count", "fortran", "module",
			"procedure", "profile", "savepoint", "sqlstate", "tracing",
			"quota", "schema", "statement_id", "transaction", "read",
			"scn", "statistics", "triggers", "real", "section", "stop",
			"truncate", "recover", "segment", "storage", "under",
			"references", "sequence", "sum", "unlimited", "referencing",
			"shared", "switch", "until", "resetlogs", "snapshot", "system",
			"use", "restricted", "some", "tables", "using", "reuse",
			"sort", "tablespace", "when", "role", "sql", "temporary",
			"write", "roles", "sqlcode", "thread", "work", "rollback",
			"sqlerror", "time", 
		};

		Set<String> set = new HashSet<String>();
		for (String keyword : keywords) {
			set.add(keyword);
		}

		KEYWORDS = Collections.unmodifiableSet(set);
	}
	
	/**
	 * Converts name of the class to the table.
	 * 
	 * @param className
	 *            name of the entity class 
	 * @return name of the table of mapped entity class
	 */
	@Override
	public final String classToTableName( String className ) {
		String tableName = super.classToTableName( className );

		switch (tableName.charAt(tableName.length() - 1)) {
			case 'x':
			case 's':
				tableName += "e";
				break;
			case 'y':
				tableName = tableName.substring(0, tableName.length() - 1);
				tableName += "ie";
				break;
			default:
		}
		
		tableName += "s";
		
		
		return tableName;
	}

	/**
	 * Converts property to column name. If name is a keyword i will be converted to: 
	 * <code>nnazwa</code>
	 * 
	 * @param propertyName
	 *            name of property
	 * @return name of the column of mapped property
	 *  
	 */
	@Override
	public final String propertyToColumnName( String propertyName ) {

		String columnName = super.propertyToColumnName( propertyName );

		if ( KEYWORDS.contains( columnName ) ) {
			columnName = columnName.charAt( 0 ) + columnName;
		}

		return columnName;
	}

}
