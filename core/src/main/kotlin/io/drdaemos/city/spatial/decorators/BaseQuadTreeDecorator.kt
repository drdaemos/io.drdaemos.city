package io.drdaemos.city.spatial.decorators

import io.drdaemos.city.spatial.*

abstract class BaseQuadTreeDecorator<T> (private val tree: QuadTreeNode<T>) : QuadTreeNodeInterface<T> by tree, Iterable<QuadTreeNodeInterface<T>> by tree
