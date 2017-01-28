package com.github.i49.spine.model;

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
	
	@Override
	public String toString() {
		return root.toString();
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
			if (level <= 0 || label == null) {
				throw new IllegalArgumentException();
			}
			
			EntryImpl parent = null;
			if (level == lastLevel) {
				parent = lastEntry.parent;
			} else if (level > lastLevel) {
				parent = lastEntry;
				int nextLevel = lastLevel;
				while (++nextLevel < level) {
					parent = parent.appendChild(new EntryImpl(nextLevel, "", null));
				}
			} else if (level < lastLevel) {
				parent = lastEntry.parent;
				int nextLevel = lastLevel;
				while (nextLevel-- > level) {
					parent = parent.parent;
				}
			}
				
			this.lastEntry = parent.appendChild(new EntryImpl(level, label, location));
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

		private final int level;
		private final String label;
		private final URI location;

		private EntryImpl parent;
		private final List<EntryImpl> children = new ArrayList<>();
		
		private EntryImpl() {
			this.level = 0;
			this.label = null;
			this.location = null;
		}

		private EntryImpl(int level, String label, URI location) {
			this.level = level;
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
			List<Entry> entries = Collections.unmodifiableList(children); 
			return entries.iterator();
		}
		
		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			buildString(b);
			return b.toString();
		}
		
		private void buildString(StringBuilder b) {
			if (level > 0) {
				for (int i = 0; i < level; i++) {
					b.append("#");
				}
				b.append(" ").append(label);
				if (location != null) {
					b.append(" [").append(location.toString()).append("]");
				}
				b.append("\n");
			}
			for (EntryImpl child: children) {
				child.buildString(b);
			}
		}
		
		private EntryImpl appendChild(EntryImpl entry) {
			this.children.add(entry);
			entry.parent = this;
			return entry;
		}
	}
}
