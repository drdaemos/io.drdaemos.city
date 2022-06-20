package io.drdaemos.city.data.decorators

import io.drdaemos.city.data.*

abstract class BaseQuadTreeDecorator<T> (private val tree: QuadTreeNode<T>) : QuadTreeNodeInterface<T> by tree, Iterable<QuadTreeNodeInterface<T>> by tree
