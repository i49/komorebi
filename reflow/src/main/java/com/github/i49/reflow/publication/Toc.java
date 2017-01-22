package com.github.i49.reflow.publication;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Table of contents for a publication.
 */
public class Toc implements Iterable<Toc.Entry> {
	
	private final Entry root;
	
	/**
	 * Creates a builder to build a table of contents.
	 * @return created builder.
	 */
	public static Builder builder() {
		return new Builder();
	}

	private Toc(Entry root) {
		this.root = root;
	}
	
	@Override
	public Iterator<Entry> iterator() {
		return root.iterator();
	}

	/**
	 * An entry in this table of contents.
	 */
	public static interface Entry extends Iterable<Entry> {
		
		/**
		 * Returns the label of the entry.
		 * @return the label of the entry.
		 */
		String getLabel();

		/**
		 * Returns the link target of the entry.
		 * @return the link target of the entry.
		 */
		URI getLocation();
		
		/**
		 * Returns whether this entry has any child entries or not.
		 * @return {@code true} if this entry has children, {@code false} otherwise.
		 */
		boolean hasChildren();
	}
	
	/**
	 * A builder class to build table of contents. 
	 */
	public static class Builder {
		
		private final EntryImpl root;
		private EntryImpl lastEntry;
		private int lastLevel;
		
		private Builder() {
			this.root = new EntryImpl();
			this.lastEntry = root;
			this.lastLevel = 0;
		}
	
		public Builder append(int level, String label, URI location) {
			if (level <= 0 || label == null || location == null) {
				throw new IllegalArgumentException();
			}
			
			EntryImpl parent = null;
			if (level == lastLevel) {
				parent = lastEntry.parent;
			} else if (level > lastLevel) {
				parent = lastEntry;
				int nextLevel = lastLevel + 1;
				while (nextLevel++ < level) {
					parent = parent.appendChild(new EntryImpl());
				}
			} else if (level < lastLevel) {
				int nextLevel = lastLevel;
				while (nextLevel-- > level) {
					parent = parent.parent;
				}
			}
				
			this.lastEntry = parent.appendChild(new EntryImpl(label, location));
			this.lastLevel = level;
				
			return this;
		}
		
		/**
		 * Returns the built table of contents.
		 * @return the built table of contents.
		 */
		public Toc build() {
			return new Toc(this.root);
		}
	}
	
	private static class EntryImpl implements Entry {

		private final String label;
		private final URI location;

		private EntryImpl parent;
		private final List<Entry> children = new ArrayList<>();
		
		private EntryImpl() {
			this.label = null;
			this.location = null;
		}

		private EntryImpl(String label, URI location) {
			this.label = label;
			this.location = location;
		}
		
		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public URI getLocation() {
			return location;
		}
		
		@Override
		public boolean hasChildren() {
			return !children.isEmpty();
		}

		@Override
		public Iterator<Entry> iterator() {
			return Collections.unmodifiableList(children).iterator();
		}
		
		private EntryImpl appendChild(EntryImpl entry) {
			this.children.add(entry);
			entry.parent = this;
			return entry;
		}
	}
}
