# 业务开发存在的问题：

1、不易理解，不宜维护，不熟悉的人难以快速上手

2、改一个bug或增加一个小功能引出很多其他问题，测试难

# 产生问题的原因：

1、业务逻辑、流程本身就复杂，而且有很多定制的小细节

2、后端soa化之后，相同功能抽象成服务，在一个模块中，往往集成了原来各个产品线的逻辑，业务分支极具增大，导致代码里面充斥这if 
else、重复代码、大代码段等等。相同的操作，不同产品线的业务逻辑不同，例如下单，横向来说，不同产品线、不同类型的组合有几十种业务逻辑分支，纵向说，不同产品线所需步骤也不相同，这种情况下代码如何组织？

3、业务需求多，往往一个需求过来要很快上线，没时间详细设计，代码很难优雅

4、同一个项目代码，经手的人多，每个人的代码风格、技术水平、对业务的熟悉程度都不一样

# 解决方式：

1、合理拆分模块，设计架构，专业的事交给专业的模块

2、模块内部合理设计代码架构，使逻辑清晰易于开发和维护

# biz业务组件

业务组件编程模型/设计模式，能改很大程度上解决当业务逻辑变得越来越复杂之后，带来的代码开发维护难的问题，让新手也能顺利开发业务需求。

业务组件定义：复杂的业务逻辑和业务流程拆解成小块的链式调用完成整个请求的处理，每个小块称之为一个业务组件，组件的特点是高内聚、低耦合、多态、内部是结构化的

业务组件特点：

高内聚：业务组件是功能单一，可复用的业务逻辑小块，只做一件小事，为同步、异步组件，同步就一个核心方法，异步组件将提交、获取结果、处理结果三个步骤合在一起来提高内聚性，管理维护起来方便，单测可针对组件进行编写

多态：例如下单是个同步操作，下单组件就是继承自同步组件的具体组件定义，而不同的产品，下单的逻辑不同，可以再衍生出不同的实现类

结构化：具体组件接口定义与最终实现类直接，加入一次抽象实现，作为业务模板、公共逻辑实现，可以提高代码的结构化程度，也减少了重复代码

多版本：同一个组件实现，也可以有多个版本，可以用于业务降级等场景

低耦合：不同的业务操作拆分成了多个组件，每个组件有很多具体实现，每个实现可能还有多个版本，就需要组件路由器来管理和路由，调用者无需关心具体组件，调用时只需调用组件路由器，路由器会自动通过运行时的上下文信息找到对应的组件实现

函数式调用、组件链：对组件的调用（使用）可以通过函数式调用的方式来使用，非常方便，另外，组件里也可以再调用其他组件，合作来完成复杂的业务逻辑


# biz业务组件与工作流引擎的区别？

工作流引擎解决的是业务流程长、步骤多，在代码中，不容易从顶层观察，定义在xml中更容易理解整个流程，步骤之间可以解耦，灵活组装。

业务组件主要解决的是业务分支多的问题，不但解决纵向上步骤（操作）解耦问题，还解决横向上业务分支多，代码圈复杂度高的问题。
