# QCC Whitepaper

---

# Chapter 1　What Is QCC

---

## 1.1　The Core Definition of QCC

QCC is a blockchain operating system built for **trusted ledgers and trusted data**.

It is grounded in **trustworthy on-chain byte data**, driven by **rule-based execution**, and oriented toward **unified circulation of assets and data**. QCC serves two roles simultaneously: as a **value network**, handling the issuance, transfer, and ownership of assets; and as a **data network**, handling the trusted recording, structured publication, and long-term preservation of real-world information.

QCC fundamentally reframes the relationship between value and data: it does not treat asset speculation as its endpoint (though speculation remains possible), but takes **supporting the complex business logic of the real world** as its starting point.

> QCC is not a transfer-only public chain, not a simple notarization chain, and not an ordinary smart contract platform. QCC's goal is not for the core team to build every application — it is to provide a foundational on-chain runtime environment for third-party developers, enterprises, and institutions.

---

## 1.2　The First Principles of QCC

The first principle of blockchain is not token issuance — it is **immutability**.

The intrinsic value of immutability should not be limited to transfer records alone. It must extend further:

- **Trusted Data**: any byte data can carry an immutable, verifiable origin
- **Trusted Ledger**: changes to assets, ownership, and rules leave a permanent, traceable trail
- **Verifiable Behavior**: on-chain operations carry clear semantics and verifiable consequences
- **Auditable Rules**: business rules are public, transparent, and confirmable
- **Traceable State**: the system state at any point in time can be reconstructed

QCC advances along this first principle — extending immutability from a "transfer ledger" to a "trusted byte network."

---

## 1.3　QCC Is a Blockchain Operating System

If you have used iOS, you already understand the structural logic of QCC.

iOS provides a unified runtime environment for applications on mobile devices; QCC provides a unified trusted runtime environment for on-chain business. The two systems are architecturally isomorphic — not as a metaphor, but as a genuine design correspondence.

| Dimension | iOS | QCC |
|-----------|-----|-----|
| **System Role** | Mobile device operating system | Blockchain operating system |
| **First Principle** | Device control and application security | Immutability, extended to trusted data, trusted ledger, trusted rules |
| **Native Runtime Unit** | App / Process | Container |
| **Application Hosting** | Each App is an independent hosting unit | Each Container is an independent hosting unit for tokens, data, and any on-chain business |
| **Permission Model** | Camera, gallery, location — Apps request per need | Freeze, mint, permission update — Rule instances enabled independently per container |
| **Permission Boundary** | One App's permission does not propagate to others | One asset's freeze capability does not apply to other assets |
| **Lifecycle** | Install, launch, foreground, background, suspend, terminate | Create, publish, update, freeze, deactivate |
| **System Isolation** | Apps are isolated from each other by default | Containers are isolated by default — rule boundary, asset boundary, state boundary each independent |
| **Developer Tiers** | Individual developer / Enterprise developer | User-level rules / Financial-level rules / System-level rules |
| **System Capability Exposure** | Apple defines capabilities; developers invoke within boundaries | QCC defines rule capabilities; developers instantiate within container boundaries |
| **Application Publishing** | Developers publish Apps subject to system rules | Developers publish containers and rules via on-chain transactions |
| **Permissionless** | Subject to App Store review | No identity verification, no manual approval — anyone can publish a container |
| **Virtual Machine** | iOS itself is an OS, not a VM | QCC itself is an OS; sub-chains are VMs running on top of it |
| **Ecosystem Goal** | Enable developers to build a vast App ecosystem around the system | Enable developers to build financial, notarization, AI, social, and enterprise applications around containers |
| **System Endgame** | Become the application foundation on devices | Become the foundation for trusted data, trusted ledgers, and on-chain applications |

---

## 1.4　Core Concept Mapping

Once the structural logic of iOS is understood, every core design decision in QCC has a clear intuitive counterpart:

**Container = Process**　A native on-chain runtime unit that developers can request, own, and extend. Each container has an independent business boundary, participating in system-level verification, execution, indexing, and state transitions. A container can host token assets, entity data, rule instances, and any on-chain business logic.

**Block = Data Boundary**　A block's primary role in QCC is not to serve as the main body of the hash chain, but to define the data boundary of a container's contents — delimiting the submission scope of each batch of containers, and handling packing, ordering, and height advancement. The true hash relationship exists **between containers**: each container holds a hash reference to its predecessor, forming a verifiable container chain.

**Rule = Permission**　Rules are not globally shared code — they are permission instances that take effect independently within a container. The same rule capability is isolated across containers: Asset A may be freezable while Asset B is not; Asset A may be mintable while Asset B has a fixed supply. Developers update their container's rules via rule transactions, with no manual approval required.

**Rule Instantiation = Smart Contract Alternative**　Traditional smart contracts deploy arbitrary code onto the chain, interpreted by a virtual machine, with blurry boundaries and poor auditability. QCC's alternative: rules are pre-defined as system-level capabilities, developers instantiate rules into containers, and rules take effect independently within container boundaries, forming the business execution logic of that container.

> **QCC natively rejects "writing code (contracts)" on-chain. It advocates "configuring rules" on-chain instead.**

**Container Lifecycle = Process Lifecycle**　From creation, activation, and operation through freezing and deactivation — each stage has corresponding rule constraints and state records, fully traceable on-chain.

**Transaction = System Call (Syscall)**　All on-chain actions are expressed as transactions — not only transfers, but also rule publishing, asset creation, data upload, permission changes, and container lifecycle management. Transactions are the **unified action language** of the on-chain world, analogous to system calls in an operating system.

**Sub-chain = VM**　A derivative system formed by a third party configuring balance state machines, rule state machines, and index state machines on top of QCC's raw data — a virtual ledger environment running on QCC. QCC is the OS; sub-chains are VMs.

---

## 1.5　QCC's Definition of Decentralization

QCC adopts a layered definition of decentralization.

At the operating system layer, QCC is fully open and permissionless: anyone can become a developer, anyone can publish a container, anyone can build applications through rule transactions — no identity verification, no manual approval required.

At the core role layer, QCC adopts a phased opening strategy, advancing progressively as network security, anti-Sybil capabilities, and ecosystem maturity develop.

QCC's decentralization begins with: **open participation, open development, open publishing**.

---

## 1.6　QCC's Unified Transaction Model

The "transaction" in QCC is not the narrow concept of a transfer.

In QCC, any action that can be submitted to the ledger, form a trusted record, and trigger a state change, rule confirmation, or ownership transfer constitutes a unified transaction. A transfer is merely one type.

The following actions all enter the system as transactions in QCC:

- Peer-to-peer asset transfer
- Rule publishing, updating, and instantiation
- Asset creation and definition
- Data upload and on-chain notarization
- Permission changes and container lifecycle management
- System-level rule invocation

Through the unified transaction model, QCC brings all trusted data into a single ledger system.

---

## 1.7　QCC's Ecosystem Goal

QCC does not take responsibility for building every application itself. QCC is responsible for continuously evolving the underlying system, enabling third parties to freely build on top of containers:

- **Financial Applications**: stablecoins, bills, clearing and settlement, institutional assets
- **Notarization Applications**: financial reports, contracts, vouchers, trusted records
- **Social and Messaging**: on-chain identity, trusted communications
- **AI Trusted Ledger**: AI behavior records, data provenance notarization
- **Enterprise Business Systems**: auditable business processes and ownership management

QCC's endgame is to become **the foundation for trusted data, trusted ledgers, and third-party on-chain applications**.

---

## Chapter Summary

QCC is not a transfer chain, not a notarization chain, and not a smart contract platform.

QCC is a **blockchain operating system for trusted ledgers and trusted data** — containers are processes, blocks are data boundaries, rules are permissions, rule instantiation replaces smart contracts, and unified transactions are the system call language.

- Transactions define actions
- Containers host objects
- Blocks enforce data boundaries
- Assets define ownership
- Business logic natively rejects "writing code (contracts)" on-chain — it advocates "configuring rules"
- State machines drive execution
- A minimalist byte protocol replaces text-based protocols
- Security first: post-quantum cryptography and rule boundaries

These core abstractions together form the body of QCC. QCC is not a single-purpose transfer chain — it is a container-based public chain system capable of unifying transactions, assets, data, rules, and system coordination. A blockchain operating system built for the complex business logic of the real world.

---

# Chapter 2　Technical Foundation and System Architecture

---

## 2.1　All On-Chain Actions Unified as Transactions

QCC's definition of "transaction" is not the narrow concept of a transfer — it is the **unified action language of the on-chain world**.

In traditional blockchains, a transaction typically represents assets moving from one address to another. In QCC, any action that can enter the ledger, form a trusted record, trigger a state change, invoke rule confirmation, or alter an ownership relationship is expressed as a unified transaction.

The following actions are all transactions in QCC:

- Asset transfer
- Rule publishing, updating, and instantiation
- Asset creation and parameter definition
- Data upload and on-chain notarization
- Permission changes and container lifecycle management
- System-level rule invocation
- Developer, merchant, and business-level on-chain submissions

QCC adopts the unified transaction model because a chain that genuinely handles complex business logic cannot invent entirely different base objects for every type of action. QCC converges different business semantics into unified transactions, then uses transaction type, asset domain (assetId), rule context, state machine routing, Gas billing logic, signature, and permission boundaries to differentiate the business consequences of each transaction.

In other words, a QCC "transaction" is not merely a payment action — it is **a standardized submission that passes signature verification, pays Gas, enters a container, participates in consensus, and is written into the trusted ledger**.

The unified transaction model delivers four core benefits:

- **Unified Verification**: all on-chain actions pass through the same signature, Gas, and validity checks
- **Unified Audit**: all key actions enter the same ledger system
- **Unified Extensibility**: new business capabilities can be added without rewriting the underlying protocol paradigm
- **Unified Understanding**: developers and nodes all build around the same foundational action model

---

## 2.2　Container Chain Structure

QCC's core chain structure is not a traditional single-layer block model — it is built on a **container chain**.

A Container is the standard on-chain unit, the business hosting unit, and the network-wide hash advancement unit — the "macro-block" of QCC. Containers have a dual nature:

- From a business abstraction perspective, a container resembles a process: an independent on-chain runtime unit that hosts assets, rules, data, and state boundaries
- From a chain structure perspective, a container is the macro-block on the global main chain — the fundamental object for confirmation, propagation, persistence, synchronization, and hash linkage

Correspondingly, a **Block** in QCC is no longer the sole main body of the global chain. Instead, it serves as a **data boundary unit** inside a container, responsible for organizing the internal data structure of the container, handling ordering, segmentation, sequential linkage, and local indexing.

QCC therefore forms a two-layer structure:

**Outer Layer: Container Chain**
- Containers form the global main chain through hash references to one another
- Containers handle height advancement, consensus confirmation, network-wide synchronization, and trusted recording
- Containers are the externally visible, standard block-level objects

**Inner Layer: Block Boundaries**
- Blocks exist inside containers
- Blocks organize the specific transactions, data, and fragment structures within a container
- Blocks define data boundaries but do not replace containers as the primary chain advancement unit

A core organizational principle of the QCC container chain:

> **One container serves exactly one assetId domain.**

This delivers three key benefits: asset domain isolation, rule domain isolation, and state domain isolation — different assets and data businesses do not mix, boundaries are clear, and state updates, freezing, minting, indexing, and auditing can all proceed independently around a single asset domain.

### 2.2.1　The Container Hash Construction Process

A container's final on-chain hash is not generated in a single step — it is bound across three distinct phases, each carrying clear technical semantics.

**Phase 1: Local Sealing (seal)**

Before entering consensus, the container completes local content sealing:

```
bodyHash    = SHA3-256(∀block: blockNumber + blockHash concatenated)
containHash = SHA3-256(containerId + bodyHash)
merkleRoot  = SHA3-256(∀block: blockHash linearly concatenated)
```

- `bodyHash`: a digest of all blocks within the container; blockNumber is included to prevent identical content at different heights from producing the same hash
- `containHash`: binds the container ID to its content, representing "a specific container's confirmation of specific content"
- `merkleRoot`: a linear digest of all block hashes, used for fast index verification

**Phase 2: Consensus Assignment (TxConsensus)**

The consensus layer (Hub) returns three fields to the container:

```
prevHash    // tailHash of the preceding container
height      // globally unique height
timestamp   // consensus timestamp
```

This step resolves: **which preceding container this container follows, and what its official position is in the global sequence.**

**Phase 3: Final On-Chain Binding (overSeal)**

After injecting the consensus-returned prevHash, the container performs its final binding:

```
tailHash = SHA3-256(containHash + bodyHash + prevHash)
```

`tailHash` is the container's official on-chain hash, and becomes the `prevHash` source for the next container. This design ensures that the physical container, logical content, and predecessor sequence are **all indispensable** — tampering with any one of them breaks the entire hash chain.

> The true hash relationship in the container chain is: `tailHash(N)` → `prevHash(N+1)` → `tailHash(N+1)`. The chain forms at the container level, not the block level.

### 2.2.2　Layered Consensus

QCC's consensus is not a single mechanism — it is completed in layers:

**System Consensus**: determines the foundational order of the chain — which roles hold confirmation authority, how governance boundaries take effect, and how staking and rewards are constrained. QCC's system consensus is jointly defined by rule containers, rule governance mechanisms, and genesis-level system rules. It is not "whatever one server decides" — it is the complete rule order of "who the system permits to confirm, and under what conditions."

**Data On-Chain Consensus**: handles how containers obtain fast confirmation and enter network-wide propagation. The current version uses a **2/3 confirmation broadcast mechanism**, with the core goal of rapidly confirming that a container has met sufficient confirmation conditions before entering official broadcast, state synchronization, and downstream processing.

### 2.2.3　Consensus Roles and Evolution Roadmap

QCC V1 uses a relatively centralized consensus role structure to ensure the underlying architecture runs stably and forms a unified order first. This is an explicit, phased engineering decision — not QCC's final definition of its long-term consensus structure.

By institutional design, confirmation authority comes not from personal will but from system rules — the system rules permit confirmation, governance rules constrain boundaries, and future evolution rules can change how confirmation roles are selected and rotated.

**V2 evolution directions** include: rotating confirmation mechanisms, lease-based confirmation rights, stake-based confirmation allocation, rule multi-sig representative confirmation, multi-node joint confirmation, and governance constraints. V2 will implement rule governance multi-sig management, advancing from V1 OS-layer decentralization to V2 open-source and role-layer decentralization.

QCC does not equate "more nodes" with "more decentralized." True anti-Sybil capability is built on: nodes must satisfy on-chain rule conditions to join, participation requires staking or economic cost, and confirmation rights depend on execution weight rather than simple online presence.

**Node opening roadmap**: V1 prioritizes opening byte-data miner nodes to validate core entity data on-chain capabilities; V2 progressively opens transaction-data miner nodes with staking participation, node governance, and state coordination.

---

## 2.3　State Machine Architecture

QCC does not use a general-purpose virtual machine interpreting arbitrary code as its core execution path. Instead, it uses a **state machine architecture** as its core execution backbone.

The fundamental idea: all on-chain writes are completed through transactions, and the real consequences of transactions are executed by different state machines within clearly defined boundaries. QCC decomposes the system into multiple state-processing units with well-defined responsibilities — each type of state machine handles only its own domain.

**Balance State Machine**: handles value-flow state changes including asset balances, deductions, additions, freezing, unfreezing, allocation, and ownership. Serves not only the native coin but also stablecoins, rule assets, and future financial asset objects.

**Rule State Machine**: handles rule publishing, updating, enabling, disabling, instantiation, permission constraints, and rule enforcement boundaries. Determines whether assets in a container can be frozen, minted, dividend-distributed, or merchant-callback-enabled.

**Index State Machine**: organizes raw on-chain records into queryable, searchable, and aggregable system views. For QCC to serve real-world business, on-chain data must not only "exist" but also be "retrievable, interpretable, and usable." The index state machine is QCC's native on-chain retrieval layer — analogous to file system indexing in an operating system — giving QCC the foundational capabilities of an on-chain database.

As the system evolves, QCC can extend additional specialized state machines for consensus credentials, data routing, reward distribution, node coordination, and sub-chain derivation. But the principle never changes: **one class of problem is handled by one class of state machine; one class of boundary is guarded by one class of state machine.**

The significance of the state machine architecture: verifiable, auditable, extensible, high-performance, high-security — system boundaries are clear, rule execution scope is controllable, and nothing is hidden inside inexplicable arbitrary code.

---

## 2.4　Rule Container System

QCC's alternative to traditional smart contracts is not a simple rejection of code — it proposes a different path entirely:

> **Do not deploy arbitrary business code on-chain. Instead, instantiate rule capabilities on-chain.**

Rules are not arbitrary code that executes freely across the entire network. They are capability units that are pre-defined by the system, callable by developers, and independently enforceable within container boundaries. Rules are closer in nature to system permissions, business constraints, behavioral boundaries, and verifiable capability interfaces.

Core characteristics of the rule system:

**Rule capabilities are pre-defined**: the system first defines capability boundaries (publishing, updating, freezing, unfreezing, minting, callback, reward distribution, permission updates, lifecycle control). Developers select, enable, and instantiate within these boundaries — they do not bypass system boundaries to deploy arbitrary logic.

**Rules take effect only within container boundaries**: if Asset A has the freeze rule enabled, Asset B is not automatically freezable; if one container has the merchant callback rule, other containers do not automatically inherit it.

**Rules serve business determinism**: the goal is not Turing completeness — it is clear behavior, visible boundaries, verifiable consequences, confirmable permissions, and auditable execution.

Rule system layers: user-level (for developers and merchants), asset-level (for asset publishing and management), system-level (for Gas, system addresses, and network-wide operation boundaries), and genesis-level (defining initial system order and super addresses).

QCC's rule container system is not a "weakened contract" — it is a rule operating system designed for real assets, real data, and real business boundaries. It brings lower risk of rule failures, more auditable system logic, and more predictable Gas and execution boundaries.

---

## 2.5　Unified Asset and Data Model

Traditional blockchains typically treat "assets" and "data" as objects from two different worlds: assets transfer value, data is merely supplementary description or external reference. QCC reconstructs this with a unified model.

In QCC, **an asset is the unified identifier of the ownership domain, rule domain, and state domain of an on-chain business object**, spanning two major directions:

**Financial Assets**: native coins, stablecoins, bill-type assets, equity-type assets, and other transferable value objects — emphasizing issuance, transfer, freezing, minting, distribution, ownership, and clearing and settlement.

**Data Assets**: text, images, orders, financial reports, contracts, vouchers, logs, application content, and any byte data — emphasizing publication, reference, notarization, versioning, ownership, trusted provenance, and long-term preservation.

Both types of assets in QCC share a unified structural logic: both have an assetId, both can enter the system via transactions, both can be bound to rules, both can enter containers, both can be processed by state machines, and both can form verifiable, auditable, and traceable on-chain records.

QCC holds that: **data itself can become an asset object that is defined, published, owned, and processed by rules.** Orders, financial reports, contracts, receipts, and audit trails can all enter the trusted ledger system directly — no longer dependent on centralized databases and screenshot proof.

---

## 2.6　Netty-Based Byte Protocol

QCC not only reconstructs on-chain objects — it also reconstructs the chain's internal communication model.

Traditional web systems communicate between services via HTTP and text protocols. But for a chain system that must handle high-frequency internal coordination, container transmission, state synchronization, index updates, and consensus interaction, text protocols introduce excessive redundancy and parsing overhead. QCC therefore adopts a **Netty**-based byte-level internal communication protocol.

Design principles:

- **Byte-oriented, not text-oriented**: internal communication uses byte frame structures rather than JSON/HTTP, reducing transmission redundancy, lowering parsing overhead, and improving boundary determinism
- **Frame boundary-oriented, not page interface-oriented**: each internal request is a byte-level submission with a well-defined boundary, a well-defined operation, and a well-defined payload
- **Unified operation semantics**: internal module calls are organized around unified operation tags, unified payload structures, and unified response models — making the internal network behave more like a system bus
- **Frame protocol boundaries for large payloads**: for standard 256K-level payloads and larger containerized data scenarios, the protocol layer uses explicit frame boundaries, segmented transmission, and reassembly mechanisms

`TxConsensus` byte serialization is a direct embodiment of this design — when consensus objects are transmitted between nodes, they are encoded entirely in pure `ByteBuffer` byte format, including nodeId, containerId, bodyHash, containHash, assetId, prevHash, height, timestamp, and all other fields, with zero text format overhead.

> **Nodes do not call each other like web pages. They collaborate at the byte level, like modules within a system.**

---

## 2.7　Post-Quantum Key and Account System

QCC's security architecture is oriented toward the post-quantum era from the very beginning. Traditional blockchains rely heavily on classical public-key cryptography — but as quantum computing capabilities develop over the long term, classical signature systems face potential risk. QCC therefore prioritizes a **post-quantum signature approach** in its key system design.

### 2.7.1　Signature Algorithm: ML-DSA-87

QCC's key system uses **ML-DSA-87** (corresponding to NIST FIPS 204, formerly known as Dilithium5) — a post-quantum signature standard based on lattice cryptography:

| Parameter | Specification |
|-----------|---------------|
| Algorithm Standard | ML-DSA-87 / FIPS 204 |
| Public Key Length | 2,592 bytes |
| Private Key Length | 4,864 bytes (standard private key) |
| Implementation Libraries | liboqs (client) / BouncyCastle (server) |
| Cross-Platform Unification | Unified to ML-DSA-87 across all platforms; early Dilithium5 Java implementation retired |

> QCC's early server implementation used BouncyCastle's Dilithium5. The current system has been fully unified to the ML-DSA-87 standard to ensure cross-language, cross-platform (Java / Dart / third-party) key consistency.

### 2.7.2　Key Derivation: Mnemonic + Payment Password Two-Factor

QCC's key derivation is not a simple "mnemonic → private key" process. It is a deterministic derivation in which **both the mnemonic and the payment password participate**:

```
seed = PBKDF2-SHA512(
    input      = mnemonic (24 words, UTF-8 bytes),
    salt       = "ML-DSA-87:qcc-wallet:" + paymentPassword,
    iterations = 210,000,
    keyLength  = 32 bytes
)

ML-DSA-87 key pair = DeterministicMLDSA87.generateFromSeed(seed)
```

The key point: the payment password is injected as the **salt parameter** in PBKDF2, not used to encrypt the private key after the fact. This means the payment password participates in the seed generation process at the cryptographic level — it is one of the inputs to the key derivation function.

QCC's account recovery model is therefore:

> **Mnemonic × Payment Password → Deterministic Seed → ML-DSA-87 Key Pair → Address**

Without either factor, the same private key and address cannot be recovered.

### 2.7.3　Mnemonic Leakage No Longer Means Total Loss

In traditional wallet models, a leaked mnemonic immediately means full account exposure. QCC's two-factor derivation model changes this:

Even if the mnemonic is accidentally leaked, an attacker without the correct payment password cannot recover the corresponding ML-DSA-87 private key. The user still has an **emergency response window** after discovering the risk — time to transfer assets, rebuild the account, and abandon the old address.

This protection still depends on the payment password having sufficient strength. If the payment password is too simple, brute-force enumeration remains theoretically possible. Upon suspecting mnemonic leakage, users should immediately execute asset transfer and account reconstruction.

### 2.7.4　Address Format

QCC standard addresses (single-signature) are generated from public keys through the following steps:

```
hash32   = SHA3-256(publicKey)
hash20   = hash32[0..19]                          // first 20 bytes
payload  = 0x00 + hash20                          // version (1 byte) + hash20
checksum = SHA3-256(SHA3-256(payload))[0..3]      // double SHA3-256, first 4 bytes
address  = "Q" + Base58(payload + checksum)       // 25-byte encoding, Q prefix
```

QCC addresses begin with **`Q`**, have a fixed length, carry a built-in checksum, and can be validated locally on the client side.

### 2.7.5　Multi-Signature Addresses

Multi-signature addresses are derived jointly from multiple participants' public keys and threshold parameters, supporting the M-of-N signature model:

```
canonical   = QCCMS + ruleVersion + chainId + M + N + sorted(pubKeys)
contentHash = SHA3-256(canonical)
hash20      = RIPEMD160(contentHash)
payload     = 0x05 + hash20
checksum    = SHA3-256(SHA3-256(payload))[0..3]
address     = "M" + Base58(payload + checksum)
```

Key design decisions:
- **Public keys sorted by byte order**: participant order does not affect the address, ensuring determinism
- **chainId participates in encoding**: the same set of public keys produces different addresses on different chains, preventing cross-chain replay attacks
- **Version field**: `0x05` distinguishes multi-sig addresses from standard addresses (`0x00`); `M` prefix is visually distinguishable
- **MAGIC header `QCCMS`**: explicitly identifies the encoding format, reserving space for future rule upgrades

Multi-signature addresses begin with **`M`**, visually distinct from standard `Q` addresses. Combined with rule containers, multi-sig can further layer freeze rules, permission updates, lifecycle controls, and more — suitable for enterprise, institutional, and high-value asset scenarios.

### 2.7.6　Account Security Layers

QCC's account system provides security at three layers:

| Layer | Mechanism | Protection |
|-------|-----------|------------|
| On-chain | ML-DSA-87 post-quantum signature | Transaction validity and ownership authenticity |
| Derivation | PBKDF2-SHA512 two-factor seed | Key cannot be recovered from a single factor |
| Client | Local payment password protection | Usability and protection against key misuse |

QCC's choice of the post-quantum route is to answer a more fundamental question:

> If on-chain data and on-chain assets are to carry long-term trusted responsibility, their signature system cannot only satisfy today — it must be oriented toward the future.

---

## Chapter Summary

Chapter 2 answers the question: how does QCC, as a blockchain operating system, hold up technically?

QCC's answer is delivered through a set of mutually reinforcing, phased system abstractions:

- **Unified Transactions**: converging all on-chain actions into standardized submissions
- **Container Chain + tailHash**: three-phase hash binding between containers — physical container, logical content, and predecessor sequence are all indispensable
- **Layered Consensus + Phased Evolution**: V1 centralized launch; V2 staking and rule multi-sig progressively opening confirmation rights
- **State Machines**: a well-defined execution backbone — verifiable, auditable, extensible
- **Rule System**: rule instantiation replacing arbitrary contract deployment
- **Unified Asset Model**: value objects and data objects brought into the same ledger logic
- **Netty Byte Protocol**: byte-level internal coordination supporting 256K-level payloads
- **ML-DSA-87 + Two-Factor Derivation**: post-quantum signatures with mnemonic × payment password seed mechanism
- **Dual Address System**: Q-prefix single-sig addresses, M-prefix multi-sig addresses, chainId preventing cross-chain replay

These structures together form QCC's technical foundation. QCC is not adding features to an old public chain — it is redefining what a blockchain that serves real-world complex business, trusted data, and long-term security should look like at its core.

---

# Chapter 3　What Can QCC Do

---

## 3.1　QCC's Two Primary Application Directions

QCC's application scope appears broad, but at its core it resolves into two directions: **financial infrastructure** and **trusted data service infrastructure**.

This division is possible because QCC itself possesses two classes of foundational capabilities:

The first is **value, asset, rule, and ledger coordination capability** — enabling QCC to handle not only transfers but also stablecoins, asset tokens, decentralized exchange, merchant payments, clearing and settlement, supply chain bills, and cross-ledger coordination.

The second is **byte data on-chain capability, trusted recording, rule constraints, state machine execution, and index retrieval** — making QCC not just a financial chain but also a trusted data foundation capable of serving AI, judicial, billing, enterprise audit, social content, mini-applications, and any scenario requiring "trusted records + verifiable rules + traceable state."

> As a blockchain operating system, QCC can serve as the foundation for both new financial structures and trusted data services. This is one of QCC's key differentiators from traditional public chains — many chains can only expand along the axis of asset transfer, while QCC, having unified assets, rules, state machines, and byte data, can naturally grow outward along both directions.

### 3.1.1　Financial Infrastructure

On the financial side, QCC can simultaneously serve two worlds: **open finance and on-chain native finance** (stablecoins, rule assets, open trading markets, decentralized matching, on-chain payments, native clearing and settlement), and **the digital extension of real-world financial systems** (merchant payments, regional ledger coordination, cross-border settlement, bill circulation, supply chain finance, and institutional-grade ledger systems).

QCC can cover both because it understands "finance" as a complete ledger and rule system: assets can be defined, rules can be bound, states can be executed, payments can be confirmed, records can be audited, and multi-party collaboration can be written into a shared trusted ledger.

### 3.1.2　Trusted Data Service Infrastructure

On the trusted data side, QCC's focus is not simply "putting data on-chain." It is about ensuring that data, once on-chain, carries: verifiable provenance, long-term preservation, rule constraints, indexed queryability, auditable traceability, and a unified ledger relationship with assets and actions.

QCC's trusted data service is not about "hashing a file and writing it to the chain." It is about letting real-world business data enter the on-chain system in a structured way, participating in rules, state logic, and audit trails. QCC is therefore not a simple notarization chain — it is a trusted data service infrastructure.

---

## 3.2　Financial Infrastructure: What QCC Can Serve

QCC's financial offering is not a fixed set of products — it is a continuously extensible set of foundational financial capabilities. The following are the most representative application scenarios.

### 3.2.1　Basic Transfers and On-Chain Payments

The most fundamental financial capability of any chain is the transfer. But QCC's understanding of a "transfer" goes beyond sending digital balance from one address to another — it is an on-chain payment action that is confirmable, accountable, receipted, and capable of entering the rule system.

QCC's basic transfers and on-chain payments can serve: peer-to-peer asset transfers, merchant-to-user payment collection, cross-regional micropayments with instant settlement, on-chain payment capabilities in multi-asset environments, and as the entry point for subsequent clearing, callbacks, and voucher generation.

Unlike traditional payment chains, QCC payments are not isolated actions — they naturally combine with asset rules, merchant callbacks, state machine updates, voucher records, data audit trails, and downstream auditing. In QCC, a payment is not just "money from A to B" — it can simultaneously be: confirmation of an order completion, a trigger condition for a merchant callback, the starting point for generating an on-chain voucher, and the settlement result of a cross-ledger collaboration.

### 3.2.2　Stablecoins and Asset Tokens

QCC can natively host stablecoins, as well as asset tokens in a broader sense — on-chain asset representations corresponding to real business objects, real ownership relationships, and real rule structures.

In QCC, asset tokens can correspond to more than an abstract currency — they can represent: financial reports, orders, dividend rights, bills, vouchers, equity records, institutional internal accounting objects, and other asset units with clear business meaning.

QCC's asset tokenization is not about creating more empty speculative symbols. It is about letting business objects that have long been scattered across databases, spreadsheets, paper documents, and enterprise systems enter the on-chain world for the first time — becoming assets that can be defined, rule-bound, audited, allocated, frozen, updated, dividend-distributed, and accessed by trading markets.

Stablecoins are the most intuitive and mature class of asset representation. On QCC, a stablecoin is not only a payment instrument — it can further enter: cross-border payments, merchant collection, regional clearing and settlement, on-chain callbacks, open financial markets, and institutional ledger coordination.

> Stablecoins solve the problem of "value anchoring." Asset tokens solve the problem of "how business objects are expressed on-chain." Together, they form an important foundation of QCC's financial capabilities.

### 3.2.3　Decentralized Exchange and Rule Asset Markets

When assets can be defined, rules can be bound, and ledgers can be uniformly recorded, trading markets naturally emerge. QCC not only hosts stablecoins and asset tokens — it also supports decentralized exchange and rule asset markets.

QCC provides its own **Open / Win transaction model**:

The initiating party creates an `Open` transaction, generating an on-chain transaction hash as the base object for subsequent market matching and settlement. The counterparty matches based on that hash and the corresponding conditions, completing on-chain settlement confirmation. The `Open` transaction currently supports at least three typical forms: random distribution (e.g., random airdrops), equal distribution (e.g., equal airdrops), and exchange-rate trading (e.g., spot trading objects).

In spot trading scenarios, an `Open` transaction carries explicit conditions — the counterparty can only complete the trade by satisfying conditions set by the initiating party. If a spot `Open` cannot be fully consumed in a single match, the remaining portion can **remain listed for up to 24 hours**, waiting for subsequent matching.

QCC's most distinctive feature here is that what is being traded is not just ordinary tokens — it can be **assets with rules**: whether transfer is permitted, whether freezing is permitted, whether dividends are permitted, whether merchant or institutional restrictions apply — these can all participate in market circulation as rule attributes of the asset. This makes QCC suitable not just for DEX but for hosting issuance and circulation systems closer to open rule asset markets.

### 3.2.4　Regional and Global Collaborative Ledger Networks

QCC's financial value is not limited to open public chain markets. More importantly, it can be used to build regional, industry-level, and cross-border collaborative ledger networks.

In this mode, different participants can implement their own ledger logic on QCC: public data enters the chain directly as bytes, private data enters as encrypted objects, hashes, or mappings; each institution maintains its own state machines within its ledger scope; regional and industry ledgers coordinate and interact through QCC.

This means banks, payment institutions, regional clearing organizations, and industry financial platforms can all theoretically build their own on-chain ledger environments on QCC, providing more flexible on-chain coordination for regional payments, cross-ledger clearing and settlement, cross-border account coordination, merchant settlement networks, inter-institutional data mapping, and voucher synchronization.

> QCC can keep not only its own ledger, but also serve as a coordination layer between multiple ledgers. This makes it closer to true financial infrastructure than an ordinary token-issuing public chain.

### 3.2.5　Merchant Payments, Clearing, Settlement, and Callback Networks

QCC is well suited for merchant payments, clearing and settlement, and payment callback networks. In merchant scenarios, what truly matters is never just "payment succeeded" — it is the entire downstream chain after payment succeeds: order status update, payment receipt generation, merchant callback trigger, account confirmation, clearing record, voucher trail, and subsequent dispute audit.

QCC's unified transaction model, rule containers, state machines, and index system can bring all these steps — normally scattered across centralized backends — into a unified trusted ledger system. A single on-chain payment can naturally produce: payment status confirmation, merchant-side business logic callback, callback execution log, settlement state transition, and downstream query and audit records.

For merchants: payments are not only confirmable, but auditable; callbacks are not only executable, but leave a trace; clearing and settlement has not only results but a complete process record; when disputes arise, the ledger, vouchers, and state chain can all be traced back.

### 3.2.6　Supply Chain Finance, On-Chain Bills, and Product Traceability

Supply chain finance, on-chain bills, and product traceability all share common requirements: extremely high demands on record authenticity, strict process ordering, precise tracking of ownership and state changes, full auditability and traceability, and frequent multi-party collaboration. These requirements align naturally with QCC's containers, rules, state machines, index capabilities, and trusted byte data on-chain architecture.

For supply chain finance, QCC can host: order records, warehouse receipts, invoices, delivery receipts, bill circulation, accounts receivable vouchers, and upstream-downstream collaboration records. For product traceability: production batch records, origin records, circulation records, logistics node information, authenticity verification data, and after-sales traceability trails. For bills: bill definition, bill ownership, bill transfer, bill state changes, and the mapping relationship between bills and order vouchers.

QCC's value in this domain is not just "letting data get on-chain" — it is bringing multi-party supply chain records into a unified trusted ledger, ensuring bills and vouchers are no longer just database fields, and ensuring product traceability is no longer dependent on a single enterprise's backend claims, but is supported by a trusted record chain.

### 3.2.7　Financial Extensibility as a Blockchain Operating System

The above represents QCC's most representative financial scenarios — not the full extent of its boundaries. Developers, enterprises, institutions, and regional networks can continue building on QCC: industry ledgers, regional clearing ledgers, institutional internal rule asset systems, more complex dividend and equity circulation mechanisms, payment networks for specific regions or industries, and trusted-data-based financial instruments.

If traditional public chains are mostly about "moving financial assets on-chain," QCC goes further — it attempts to rebuild **the operational logic of finance itself** into the on-chain environment.

---

## 3.3　Trusted Data Service Infrastructure: What QCC Can Serve

If financial infrastructure solves the problem of "value, assets, and ledger collaboration," trusted data service infrastructure solves the problem of "how data, evidence, records, and behavior can trustworthily enter the on-chain world."

QCC's value lies in not simply writing a "data hash" to the chain, but in enabling real-world byte data, business records, rule constraints, and state changes to enter the trusted ledger system in a unified structure — giving data the ability to be trustworthily published, business actions to leave a trusted trail, evidence to be trustworthily preserved, and audit logs to be trustworthily traced.

### 3.3.1　Entity Data On-Chain and Trusted Notarization

One of QCC's most differentiated capabilities is support for entity data on-chain.

"Entity data" here does not mean only a hash digest or an index pointer to an off-chain file — it means text, images, file fragments, and more real byte data bodies that can enter QCC's container structure and trusted ledger system.

This means QCC's notarization capability goes beyond proving "a piece of data once existed." It further provides:

- The data body or its trusted structure entering the chain
- Verifiable publication timestamp
- Verifiable data provenance
- Traceable data state changes
- Unified recording of relationships between data and assets, rules, and actions

QCC is therefore suited to host: text content publication, image and media fragment records, file-level business vouchers, business data such as contracts, orders, financial reports, and logs, as well as more complex fragmented data and byte-level objects.

In traditional chains, so-called "notarization" typically only writes a hash to the chain — the actual data remains in off-chain systems, still potentially subject to loss, tampering, disconnection, and interpretive difficulty. QCC's direction is to let the data body, data fragments, or their trusted byte structures themselves enter the on-chain organizational system, making "trusted notarization" a genuinely substantive data on-chain capability rather than a lightweight label.

### 3.3.2　Enterprise-Grade Auditable Records and On-Chain Database Capabilities

What enterprises truly need is never just "data on-chain" — it is: whether data is trustworthy, queryable, auditable, whether state changes are fully traceable, and whether it can directly serve business systems.

QCC's value here comes from supporting not only data entering the chain, but also rule constraints, state machine processing, containerized hosting, native index retrieval, and long-term ledger relationship preservation. This makes QCC more than a place to "record enterprise data" — it can support enterprise-grade auditable record systems.

A company's financial reports, orders, receipts, settlement records, approval logs, business callback records, departmental collaboration records, and critical system event logs — once these enter QCC, they are no longer just rows in a database. They enter a unified trusted ledger environment: with timestamps, with provenance, with state changes, and with unified relationships linking rules, payments, clearing, bills, contracts, and vouchers.

More importantly, QCC's index state machine ensures these records are not just "existing on-chain" — they can be retrieved, queried, aggregated, interpreted, and used as services.

> It is not only ledger-like — it begins to have the foundational capabilities of an on-chain database.

Enterprises have never lacked places to store data. What they truly lack is: a trusted foundation, a unified record system, a long-term auditable ledger database, and a trusted backend that connects payments, bills, rules, and business state.

### 3.3.3　Judicial Notarization, Arbitration, and Trusted Evidence

Judicial, arbitration, and dispute resolution scenarios place extremely high demands on data. In these scenarios, the critical question is not "can the data be seen" but: is the data trustworthy, has it been tampered with, when was it created, who submitted it, is it complete, and can the chain of state changes be traced?

QCC is naturally suited for these scenarios, because it can bring the most critical classes of objects in judicial and evidentiary contexts into a unified trusted ledger system: contracts, orders, receipts, chat records, transaction records, payment confirmations, callback logs, image and file byte data, arbitration process materials, and multi-party business chains.

In traditional dispute resolution, many problems stem from broken evidence chains: documents may have been modified after the fact, screenshots may be fabricated, callback logs may be lost, timestamps are hard to prove uniformly, and data from different systems do not recognize each other. In QCC, these objects can enter the trusted ledger at the moment of creation — forming a complete evidence system where publication time is provable, submitting party is verifiable, data content is checkable, state changes are trackable, and multi-party on-chain actions are mappable.

> Evidence is no longer just an off-chain screenshot or a local file — it enters a long-term verifiable, traceable, and referenceable trusted ledger environment.

QCC is therefore well positioned to serve: judicial notarization, arbitration evidence, commercial dispute resolution, multi-party collaborative evidence collection, contract performance tracking, and payment receipt and settlement dispute proof.

### 3.3.4　AI Trusted Data, Model Provenance, and Intelligent Agent Collaboration

One of the greatest challenges of the AI era is not only whether models are powerful — it is: whether data provenance is trustworthy, whether model versions are traceable, whether outputs are auditable, whether intelligent agents' behavior can be audited, and whether multi-agent collaboration has a unified ledger.

QCC has a natural advantage here because AI systems are fundamentally dependent on three trusted capabilities: trusted data input, trusted behavior records, and trusted output provenance — which align precisely with QCC's capabilities.

Within QCC's trusted data framework, AI-related scenarios can include: training data provenance records, model version publication records, inference result trails, prompt and context records, agent action chain records, ledger-based tracking of multi-agent collaborative tasks, and trusted mapping between AI outputs and business objects.

For example, in an AI Agent system: which agent initiated the task, which model was invoked, what context was used, what result was produced, and whether the result triggered a payment, callback, order, contract, or other state change — all of this can enter QCC's unified ledger and index system.

QCC's core value in the AI direction is not just providing notarization for AI — it has the potential to become the trusted data foundation for AI, the audit foundation for AI results, the collaborative ledger for intelligent agents, and the trusted behavior record layer for the AI era. Making AI go from "black-box output" to "verifiable behavior."

### 3.3.5　Social, Messaging, Mini-Applications, and Content Publishing

QCC is not only a financial ledger or an enterprise database. It is equally suited for social, messaging, mini-application, and content publishing businesses — which are long-running, high-frequency, and heavily interaction- and record-oriented.

Social systems appear to be about "sending messages," but at their core they involve: content generation, message flow, state changes, content ownership, time ordering, permission control, version trails, and long-term reference. If these are entirely dependent on centralized backends, users often cannot verify: whether content has been modified, whether publication ordering is authentic, whether selective deletion has occurred, whether history has been rewritten, and whether data and content ownership is trustworthy over the long term.

QCC's byte data on-chain capability, rule containers, state machines, and index capabilities make it well suited for: trusted content publishing, trusted message trails, mini-application-level business records, social content reference and ownership relationships, community interaction records, multi-party content collaboration, and long-term tracking.

QCC supports flexible data expression: public content can be put on-chain directly, private content can be encrypted on-chain, partial indices, hashes, and mapping relationships can be put on-chain, and content state and receipts can be on-chain.

> Providing a long-term trusted, traceable, and verifiable underlying environment for content, messaging, mini-applications, and social interactions.

### 3.3.6　Application Publishing, Developer Ecosystem, and Industry-Grade On-Chain Services

If QCC were only a protocol, it would be difficult to form a long-term ecosystem. Its true value lies in being not just the chain itself, but a foundational platform on which developers publish applications, integrate rules, organize assets, host data, and build industry-grade services.

In QCC, developers are not "just launching a token" — they can do something far more complete: publish applications, define assets, bind rules, invoke payment capabilities, use containers to host business objects, integrate with state machines and index capabilities, and form mini-application-style or industry-grade on-chain services.

Since QCC itself unifies transactions, rules, assets, data, state machines, indexes, and the byte protocol, developers building on QCC receive not just a "transfer capability" but an entire system infrastructure.

This enables QCC to serve a broad range of industry applications: industry ledger systems, enterprise-grade audit services, judicial evidence platforms, AI data platforms, supply chain collaboration systems, merchant payment middleware, social and content distribution systems, and mini-application-style business networks.

> Build your own asset system, data system, rule system, and application system on a unified trusted foundation.

This also means QCC's developer ecosystem has stronger industry plasticity — it does not merely revolve around pure financial protocol activity.

### 3.3.7　Trusted Data Extensibility as a Blockchain Operating System

The above represents QCC's most representative trusted data scenarios — not the full extent of its boundaries.

As a blockchain operating system, QCC provides not a fixed set of notarization or data products, but a continuously extensible set of trusted data foundational capabilities:

- Any byte data can enter a container as a data asset
- Data assets can be bound to rules, with lifecycles including publishing, versioning, freezing, and referencing
- State machines provide data execution and indexing capabilities
- The byte protocol supports efficient transmission and persistence of large-payload data
- The address system and signature mechanism ensure the verifiability of data ownership

If traditional chains are mostly about "proving that a piece of data once existed," QCC goes further — it is not just a notarization tool, but a trusted data operating system that brings data bodies, data actions, data rules, and data state changes together into the on-chain world. This is precisely the ultimate expression of QCC's positioning as a "blockchain operating system."

---

## Chapter Summary

Chapter 3 answers: what can QCC, as a blockchain operating system, actually do in the real world?

The answer is two parallel paths to real-world deployment:

**Financial Infrastructure Path**: beginning with basic transfers and on-chain payments, extending upward to stablecoins and asset tokens, decentralized rule asset markets, regional collaborative ledger networks, merchant payment clearing and settlement, and supply chain finance and bill circulation. QCC is not just "moving financial assets on-chain" — it is rebuilding the operational logic of finance itself into the on-chain environment.

**Trusted Data Service Path**: beginning with entity data on-chain and trusted notarization, extending upward to enterprise-grade audit records, judicial evidence systems, AI trusted behavior records, social and content publishing, developer ecosystem, and industry-grade on-chain services. QCC is not just "leaving a hash" — it gives data complete trusted structure, rule boundaries, and long-term traceability.

Both paths share the same underlying foundation: containers host objects, rules define boundaries, state machines drive execution, the index state machine provides retrieval, the byte protocol supports transmission, and ML-DSA-87 ensures security.

> QCC's endgame is to become the foundation for trusted data, trusted ledgers, and third-party on-chain applications — a blockchain operating system built for the complex business logic of the real world.

---

# Chapter 4　QCC Native Coin and On-Chain Rule Foundation

---

## 4.1　Introduction to the QCC Native Coin

The QCC native coin is the foundational value unit of the QCC public chain system and the underlying fuel of the entire on-chain runtime environment.

It does not exist merely to issue a tradable symbol. It exists to support QCC's unified transaction model, rule execution model, data on-chain model, and ecosystem coordination model. The native coin is not an accessory — it is one of the core foundations that enables QCC, as a blockchain operating system, to continue running.

In QCC's system design, the native coin serves the following roles:

- As the base payment unit for on-chain transactions
- As the cost carrier for rule invocation and rule publishing
- As the fee carrier for data on-chain and byte publishing
- As an important foundation for future staking, execution rights, and on-chain qualification boundaries
- As the unified value medium between developers, nodes, merchants, and ecosystem participants

> **The QCC native coin is the unified value foundation for all on-chain transactions, rules, data, and ecosystem collaboration.**

---

## 4.2　Unified Cost Carrier: The Native Coin and the Transaction Model

One of QCC's foundational designs is "all on-chain actions are unified as transactions." By that logic, all on-chain actions must have a unified cost carrier — and that carrier is the QCC native coin.

The native coin does not serve only one class of action — it serves the entire on-chain system:

- Pricing on-chain actions
- Billing for on-chain resource consumption
- Providing unified fuel for system operation
- Establishing unified cost boundaries for ecosystem participants

The QCC native coin and "everything is a transaction" therefore form a strict closed loop: **all actions are unified as transactions, all transactions require cost, and all costs are borne by the QCC native coin.**

---

## 4.3　Total Supply, Precision, and Unified Numerical Boundaries

The QCC native coin has a fixed total supply of **210 million**, with **no additional issuance ever** and **no freeze capability**.

On precision, QCC uses **9 decimal places**. All amounts are expressed in unified integer minimum units, with ledger computation and state processing based on Java's **`long` type**.

This means: all ledger amounts are processed as integers, eliminating floating-point errors from core state machines; the fixed supply requires no inflationary expansion; and the native coin itself is non-freezable, maintaining clear boundaries as the system's foundational value unit.

Furthermore, QCC constrains not only the native coin's precision, but the entire chain's asset system:

> **No token or asset on the QCC chain may have a precision exceeding the native coin's 9 decimal places.**

This establishes unified numerical boundaries for all chain-wide assets — whether native coins, stablecoins, rule assets, or data-type assets, they all must ultimately conform to the unified integer precision model and `long` type processing rules. The benefit: consistent calculation standards across all chain assets, unified processing of transactions, settlement, and state updates between different assets, and long-term consistency across state machines, index systems, and the ledger system.

QCC does not pursue infinite precision or infinite supply. Instead, it uses a fixed total supply, unified precision, and a unified integer ledger to establish a stable foundational value expression for the entire chain.

---

## 4.4　The Multi-Layer Role of the Native Coin

The QCC native coin's role extends well beyond paying transaction fees. It serves multiple layers across the entire system, with different significance for different participants.

**For ordinary users**: the native coin is the foundation for on-chain payments, on-chain operations, and on-chain participation — every transfer, every data upload, every on-chain action bears its cost in the native coin.

**For developers**: the native coin is the underlying cost unit for application integration, rule publishing, rule invocation, data publishing, and ecosystem building. Publishing containers, binding rules, and defining assets on QCC all involve the native coin as the interface with the system.

**For merchants and institutions**: the native coin is the unified value medium for callbacks, clearing and settlement, collaborative ledgers, and system operations. Every payment confirmation and every callback trigger by a merchant rests on the native coin as the unified cost foundation.

**For nodes and executors**: as QCC evolves, the native coin will become the basis for staking, execution right boundaries, rule governance access, and specific market qualification constraints. Confirmation rights are not determined by "whoever runs a node" — they are jointly determined through native coin staking and rule constraints.

The most critical point:

> **The QCC native coin represents not only value, but also responsibility, qualification, and the cost of system order.**

This distinguishes it from systems that treat the native coin merely as a "fee token." In QCC, the native coin more closely resembles a unified cost carrier and qualification boundary carrier for the entire on-chain operating order — embedded within the system, not attached to its exterior.

---

## Chapter Summary

Chapter 4 answers: what role does the QCC native coin actually play in the system?

Three core conclusions:

**It is the unified cost carrier**: all on-chain actions are unified as transactions, all transaction costs are borne by the native coin — forming a strict closed loop.

**It is the numerical foundation of the entire chain**: 210 million fixed supply, 9 decimal places precision, Java `long` integer ledger — constraining not only the native coin itself but establishing unified numerical boundaries for all assets on the chain.

**It is a multi-layer role carrier**: fuel for users, access cost for developers, value medium for merchants, and staking and qualification foundation for nodes. The native coin is not an isolated financial object — it is the consistent value foundation on which QCC's transaction system, rule system, data system, and future governance system all depend.

---

# Chapter 5　The Genesis Container — QCC's Zero-Height Declaration

---

## 5.1　Definition and Unique Status of the Genesis Container

The Genesis Container is QCC's only zero-height container — the root origin point of the entire QCC system at the moment of its official public network launch.

It is not a "first block" in the ordinary sense, nor an initialization file that can be supplemented later. It is a system-level primordial object that is simultaneously established at the protocol layer, node layer, and state machine layer. In QCC, all nodes, all state machines, and all subsequent container chain operation share the Genesis Container's hash and official chain ID as their common starting point.

Once the Genesis Container is published with official mainnet parameters: its timestamp is permanently fixed, its hash is permanently fixed, its content is permanently fixed, and its system status is permanently fixed. All subsequent data can only be written from Container 1 onward. The Genesis Container itself cannot be supplemented, tampered with, replaced, or regenerated after the subsequent container chain has begun running.

QCC requires the Genesis Container because it simultaneously carries transactions, rules, entity data, historical records, asset initialization, and system parameter definitions. At the zero-height moment of the protocol's birth, QCC needs one unique, unrepeatable object to make a protocol-level declaration — to tell the world the complete capability boundaries of QCC.

> **QCC's rule root, distribution root, historical root, and civilization root all begin here.**

---

## 5.2　Technical Uniqueness

The Genesis Container holds an independent technical status in QCC and does not fully follow the standard hosting approach of ordinary containers.

In the current implementation, the Genesis Container uses a specially designed reception and parsing approach — closer to a reception file parsed by a system-predefined format. It can carry richer text, rules, distributions, and multimedia structures, using a more long-term fidelity-friendly reception method rather than simply reusing the general transmission method of ordinary business containers.

This parsing capability is not "learned" by nodes after publication — it is written into the system before the public network launches. All nodes have pre-loaded the Genesis Container's source code and parsing logic, all state machines know how to read its key structures, and the official chain ID, genesis hash, and container format together constitute the network-wide startup consensus.

In terms of capacity, the Genesis Container also features special design. In the current implementation, its hosting volume is substantially larger than ordinary initialization objects — currently on the order of several megabytes, expandable according to protocol needs — sufficient to simultaneously carry genesis rules, initial distributions, messages, images, audio, short video, and encrypted content.

> **The Genesis Container's uniqueness lies not in "being an exception," but in being the only primordial object that the entire system agreed upon before launch.**

---

## 5.3　The One and Only Convergence of Three

In QCC's normal operation, transaction data, rule data, and entity data are strictly isolated — container isolation, state machine isolation, rule boundary isolation. This is the core reason QCC can maintain clear system order over the long term.

The Genesis Container is the only exception.

At the zero-height moment, QCC completes the only convergence of three in the entire chain's history through the Genesis Container:

- **Transaction Data**: carrying genesis distribution, initial balances, lock-up logic, and other value initialization structures
- **Rule Data**: carrying genesis-level rules, system-level parameters, rewards, and permission boundaries
- **Entity Data**: carrying messages, images, audio, short video, encrypted letters, and other trusted byte content

This convergence is not a violation of QCC's isolation philosophy — it is proof at a higher level:

> **QCC has always been capable of unifying value, rules, and trusted data. It is only in normal operation, for the sake of system order, that it chooses to strictly isolate them. The Genesis Container uses this one unique convergence to display the entire system's complete identity.**

---

## 5.4　Content Structure of the Genesis Container

The Genesis Container carries far more than a genesis hash or an initial balance list — it is the complete primordial structure that QCC writes onto the chain at the zero-height moment.

**Genesis-Level System Rules**: the official chain ID, genesis hash, system root parameters, genesis-level permissions and rule foundations, and the common starting point for subsequent node, state machine, and container chain consensus.

**Native Coin Initial Distribution and Lock-Up Logic**: QCC native coin initial distribution information, along with lock-up and release logic for certain addresses — genesis distribution is not only balance initialization, it can directly form long-term constraints at the genesis moment.

**Founding Team, Reward Pools, and System Resource Structures**: founding team rewards, developer reward pools, storage incentive pools, future ecosystem resource reserves, and other system-level resource structures defined by genesis rules.

**Genesis Participants' Written Messages**: historical text that officially enters the zero-height primordial container will become a permanent part of the chain's history.

**Images, Audio, Short Video, and Other Multimedia Content**: elevating the genesis record from a plain-text ledger to a multimedia primordial object with greater temporal and civilizational significance.

**The Founder's Special Historical Content**: the founder retains a higher level of historical expression space — longer written messages, explanations for the future, encrypted letters written for the next generation, and other legacy content.

> **Rules, distributions, value, text, images, sound, and historical expression — their common primordial structure is sealed together at zero height.**

---

## 5.5　Historical Significance, Civilizational Significance, and Participant Eligibility

The Genesis Container's value lies not only in its technical uniqueness — it lies in the irreversibility of its history.

In ordinary blockchains, the genesis block is typically just initial parameters and initial distributions. In QCC, the Genesis Container is simultaneously a permanent on-chain monument — recording who participated at the genesis moment, who left their words when the protocol was born, what images and sounds were permanently placed at the zero-height origin, and what spirit and legacy the founder chose to place in the chain's first object.

It therefore carries four layers of meaning: **protocol meaning** (system root object), **historical meaning** (the entire chain's only zero-height historical origin), **cultural meaning** (elevating a technical launch into a civilizational moment that can be remembered and witnessed), and **artifact meaning** (if QCC ultimately forms lasting influence, everything in the Genesis Container will become irreproducible historical digital artifacts).

For this reason, eligibility to participate in the Genesis Container has strict boundaries — it belongs only to participants who have been confirmed before the mainnet's official zero-height publication. This is determined by the protocol structure, not by operational strategy. Once the Genesis Container is published, its timestamp is fixed, its hash is permanently sealed, and all nodes take it as the sole root origin. No subsequent capital, power, or identity can ever enter the Genesis Container itself.

> **A seat in the Genesis Container is not an ordinary economic seat — it is a historical seat. It belongs only to those who were written in before zero height.**

This irreproducibility grants early participants a unique identity that no amount of money from later arrivals can compensate for — not "I bought in early," but "I was at the origin."

---

## 5.6　The Founder's Legacy Expression and Encrypted Messages

As the sole developer and founder, it is reasonable and necessary for the founder to retain a higher level of expression space within the Genesis Container. This is not centralized overreach — it is the historical position reserved for the founding role when the Genesis Container serves as the protocol's origin point.

This expression can include: longer written messages, a more complete historical account, images and multimedia content, explanations for and visions of the future, letters written for the next generation, and privately encrypted legacy content.

The design of encrypted messages deserves special mention. The founder can write encrypted letters addressed to specific recipients into the Genesis Container, simultaneously making the ciphertext, keys, and related parameters public. This gives the encrypted messages two layers of value simultaneously: **privacy** (the founder's expression is preserved in a specific way) and **verifiability** (any developer can verify its authenticity through the public parameters).

This means the Genesis Container carries not only rule and asset value, but genuine human expression and cross-generational legacy value — it is a protocol origin point that truly embodies "legacy," not just a ledger initialization file.

---

## Chapter Summary

The Genesis Container is QCC's only zero-height primordial container. Through the one unique, irrepeatable convergence of three — transactions, rules, and entity data — it serves as QCC's primordial sample, displaying to the world the complete boundary of the system's capabilities.

Once published to the public network with the official chain ID and genesis hash, its content, timestamp, hash, and system status are permanently fixed. All subsequent nodes, state machines, and container chain evolution can only continue forward from this root — it is only from Container 1 onward that the protocol's formal historical unfolding begins.

> If QCC ultimately succeeds, the Genesis Container lives forever.

---

# Chapter 6　QCC's Macroeconomic Logic

---

## 6.1　QCC as Public Chain and Operating System: Infrastructure Positioning

QCC's positioning is not merely "a public chain," nor merely "a blockchain project" — it is a decentralized operating system built for the future.

The term "operating system" here is not metaphorical. It refers to QCC bearing OS-level foundational responsibilities: it unifies the hosting of transactions, rules, assets, entity data, state machines, index systems, and on-chain applications, providing a shared trusted foundation for future developers, institutions, nodes, and business networks of all kinds.

QCC's "decentralization" is also not only about the number of nodes — it is about the **progressive decentralization of governance structure**.

QCC's governance does not begin fully formed — it advances in stages:

- **V1 Phase**: still carries a single-signature dominant character — a concentrated founding-phase push for rapid protocol birth, system formation, and foundational order establishment.
- **V2 Phase**: entering 3–9 multi-sig governance. The foundation no longer equals individual will — key protocol powers are held in multi-sig trusteeship, forming an early structure of checks and balances.
- **V3 Phase**: entering true constitutional governance. The Genesis Container serves as the protocol's original constitution; subsequent rule governance serves as an amendment system. Governance structure will evolve from multi-sig trusteeship to representative democracy and bicameral checks and balances — transforming QCC from "a system driven by its founder" to "infrastructure operated by institutions."

QCC's long-term goal is therefore not to maintain a "strong-founder chain," but to gradually complete the evolution from rule by individuals to rule by institutions — from concentrated advancement to constitutional governance.

Borrowing the language of modern political development, QCC's governance path most closely resembles:

> **Founding Phase → Institutional Trusteeship → Constitutional Governance**

This means QCC, as an operating system-type infrastructure, ultimately pursues not "who controls it," but **the ability to run long-term independent of any individual will, sustained by rules, representation, and checks and balances.**

This is the true infrastructure positioning of QCC as a public chain and operating system.

---

## 6.2　The Founding Phase: Why the Protocol Still Needs External Resources

QCC is infrastructure — but in its founding phase, it still needs external resources.

"Needing resources" here does not mean QCC must be attached to a company to exist, nor that without external funding it cannot move forward at all. More precisely, external resources mean:

> **Accelerating QCC's transition from "an independent system that has already been built" to "officially on the public network, formally organized, and globally visible."**

What QCC truly faces today is no longer the question of "whether the technology holds up." As shown in the preceding chapters, QCC has already completed:

- Core theory and the main body of the whitepaper
- Container chain and state machine architecture
- Unified transaction model
- Native coin and rule system
- The Genesis Container as a zero-height primordial object
- Open / Win market primitives and partial system capability validation

What QCC now most needs to cross is no longer the "zero-to-one" threshold — it is the following practical barriers:

**First, sustained development capability.** For a foundational protocol project that an independent developer has advanced to this stage over years, whether the founder has a stable, safe, and sustainable development environment is itself one of the real prerequisites for the protocol to continue moving forward.

**Second, mainnet and formal systematization capability.** QCC needs to advance from "testing and validation" to "officially runnable on the public network" — meaning the mainnet, browser, wallet, rule templates, basic interfaces, and genesis distribution all need a higher level of organized advancement.

**Third, minimum core team capability.** An infrastructure-type protocol cannot rely on a single person indefinitely. It needs at least 2–3 technical co-founders or core collaborators who genuinely understand the system's underlying architecture to jointly drive the protocol's continued evolution.

**Fourth, global visibility capability.** Even if the technology holds up, if the outside world cannot understand, see, or access it, QCC's rate of expansion will be severely limited. Project presentation, partnership outreach, regional deployment, and external communication are all problems that must be solved in the founding phase.

QCC therefore needs external resources in its founding phase — not because "it doesn't exist yet," but because:

> **It already exists to the point of deserving to be accelerated.**

QCC is not a project waiting to be invented. It is an infrastructure that has been independently developed to the point where its core system has taken shape — now entering the phase of formal mainnet launch and regional deployment. External resources do not determine whether it exists; they determine how quickly it advances to a higher level of protocol maturity.

---

## 6.3　First-Round Financing: USD 300K and Mainnet Launch

QCC's first-round financing has a target of **USD 300,000**.

The significance of this capital is not to "prove whether QCC is viable" — nor to create a hollow project launch. It is to formally advance a protocol that has already formed a core system skeleton to the stages of mainnet launch, key Demo completion, team formation, and initial regional contact.

The first round's core objectives can be summarized in four items:

**First, complete QCC's official public network / mainnet launch.** This is the most important technical milestone of the first round. Only with mainnet launch does QCC truly transition from "a system under validation" to "a protocol in formal operation."

**Second, complete key Demo presentations**, including but not limited to: demonstration of post-quantum accounts and mnemonic + payment password two-factor derivation capability; formal formation of the Genesis Container; demonstration of rule templates, wallet, and browser core capabilities; basic demonstrations of QCC with stablecoins, rule assets, and trusted data services.

**Third, form the minimum core team.** The first round needs to bring in 2–3 technical co-founders or long-term collaborators who genuinely understand the underlying system. QCC does not need a large team at this stage, but must complete the transition from "single-person advancement" to "minimum core team advancement."

**Fourth, establish the starting conditions for regional deployment.** The first round does not pursue global rollout — it builds the preconditions for subsequent regional validation and larger-scale expansion. This includes basic entry capability, external presentation capability, and sustained advancement capability in Singapore, Japan, and other regions.

In terms of resource allocation, the first-round USD 300,000 will primarily serve the following directions:

- Relocation of the founder and core family to a more stable and legally favorable jurisdiction, ensuring sustained development capability
- Public network / mainnet and key infrastructure advancement
- Development of browser, wallet, rule templates, Demos, and related systems
- Basic support for core technical co-founders and minimum team formation
- Foundational legal, financial, compliance, and necessary regional communication costs

The first round does not pursue "comprehensive coverage," nor does it aim to complete global expansion from the start. Its goals are more realistic — and more critical:

> **Let QCC first launch officially, run stably, complete minimum team formation, and be truly seen.**

If the preceding chapters answer why QCC is viable, then the first-round USD 300,000 answers:

> **How to advance QCC from "already built" to "officially running and globally understandable."**

---

## 6.4　The Special Nature of the First Round: Genesis Container Seats

QCC's first-round financing differs most fundamentally from ordinary projects in that it is not only a capital entry — it is simultaneously connected to Genesis Container seats.

In ordinary projects, so-called "first-round supporters" typically means only:

- Earlier entry
- Obtaining a share at lower cost
- Earlier alignment with project growth

In QCC, what first-round supporters face is not only "earlier" in an economic sense — it is:

> **Whether they can enter the Genesis Container, the zero-height primordial object.**

This is special because the Genesis Container is not an object that can be supplemented, rebuilt, or retroactively filled. As the preceding chapter made clear:

- The Genesis Container is the entire chain's only zero-height container
- Once the mainnet is published with the official chain ID and genesis hash, its content, timestamp, hash, and system status are permanently fixed
- All subsequent containers can only be written from Container 1 onward
- No subsequent capital, identity, or status can ever enter the Genesis Container itself

The special nature of first-round financing therefore lies in:

**First, it is not only a fundraising action** — it is simultaneously the confirmation process of genesis participation eligibility.

**Second, it is not only an economic seat** — it is simultaneously a historical seat.

**Third, it is irreproducible** — later supporters, even investing more resources, cannot obtain a zero-height seat.

**Fourth, it directly connects to the right of historical expression in the Genesis Container** — genesis participants are confirmed not only at the level of genesis distribution, but also have the opportunity to leave their own words or historical mark in the zero-height primordial container.

This means QCC's first-phase supporters are not ordinary "early buyers" — they are closer to:

- Genesis participants
- Witnesses to the protocol's origin point
- Zero-height historical inscribers
- Co-participants in a permanent on-chain monument

First-round financing in QCC therefore carries a dual nature:

- **Economic nature**: it provides resources for mainnet launch, team formation, and formal advancement
- **Historical nature**: it determines who will be permanently written into the Genesis Container at the zero-height moment

This dual nature is one of QCC's greatest differentiators from ordinary blockchain projects and ordinary corporate-style financing.

In QCC's context, the true scarcity of first-round financing is not "lower price" — it is:

> **It happens before the Genesis Container is permanently fixed.**

Once the mainnet launches and the Genesis Container publication is complete, no matter how many resources subsequent participants invest, they can only enter the subsequent container chain — they cannot return to zero height.

This is why QCC places such importance on first-round financing. Because it is not an ordinary first-round capital raise — it is:

> **The only intersection of protocol history and protocol capital at the zero-height moment.**

---

## 6.5　Second-Round Financing: Regional Deployment and Global Entry Points

If the first round successfully completes mainnet launch, key Demos, the Genesis Container, and minimum team formation, QCC's second-round financing will no longer be about "getting the system built" — it will be about advancing QCC from technical viability to regional viability, market viability, and global entry point establishment.

The second round should be understood not as completing a global rollout in one step, but as:

> **The launch of regional deployment or global expansion.**

The second round is not about spreading QCC everywhere in the world at once. It is about establishing entry points for rules, partnerships, nodes, and business proof-of-concepts in a number of key regions — advancing QCC from "a viable protocol" to "infrastructure beginning to be accessed globally."

Based on first-round completion and external partnership progress, the second round is projected to range from **USD 2 million to USD 5 million**, dynamically adjusted. If first-round completion is exceptionally high and external recognition and strategic partnerships form rapidly, the second round could directly enter a higher resource tier.

Core uses of second-round resources include:

- Driving regional business validation and partnership deployment
- Bringing in more foundational and productization talent
- Supporting enterprise-grade container applications and industry proof-of-concept onboarding
- Building developer networks, partnership networks, and global communication capability
- Completing compliance preparation and regional entry conditions in key jurisdictions

The key in the second round is not "how much more capital" — it is:

> **Whether QCC already has the capability to convert localized technical advantages into regional entry points and a global infrastructure narrative.**

If the first round resolves "letting QCC come alive, run, and be seen," the second round resolves:

> **Letting QCC transition from a system that has been seen to a system that different regions begin to access.**

---

## 6.6　Regional Deployment Logic

QCC's regional deployment and global entry points will not be centered on "opening many offices" — they will be centered on **establishing rules, partnerships, nodes, and business proof-of-concepts in key regions**.

**First Priority: Asia**

If the second round focuses on regional deployment, Asia is the natural first choice.

The reason is not merely geographic proximity — Asia is one of the most active regions globally for blockchain, payments, stablecoins, fintech, and developer ecosystems. At the same time, QCC's currently most realistic deployment directions — stablecoins, payment callbacks, rule assets, trusted ledgers, enterprise container applications — are all highly aligned with the Asian market.

The Asian phase can prioritize building regional partnerships and business proof-of-concepts around:

- Singapore
- Japan
- Key Southeast Asian nodes

Singapore is best suited as a financial and regional connectivity hub; Japan is best suited as a stable, safe, long-term technology and business transition point; other Southeast Asian nodes are best suited as realistic entry points for payments, users, and scenario expansion.

**Global Expansion Launch: The United States Must Be Included**

If the second round has the conditions to launch global expansion, the United States will become an essential priority region.

The reasons are straightforward: the US remains one of the world's most important centers of technology, capital, and protocol influence; US participation will significantly enhance QCC's global credibility, technical visibility, and strategic partnership space; and the US East Coast, West Coast, and South each offer different strategic anchor value across technology, capital, policy, and industry.

Once QCC moves from regional deployment to global expansion, the most logical path will be:

> **Asia as the first landing point; the United States as the core pivot for global entry.**

**Subsequent Entry Points: Middle East, Europe, and Other Regions**

After forming the dual Asia–US pivot, the Middle East and Europe will become the important entry regions for the next phase.

- **Middle East**: Dubai and similar jurisdictions friendly to virtual assets, international capital coordination, and payment innovation are the priority consideration
- **Europe**: 1–2 key countries or cities can be selected as entry points for rule assets, trusted data, and regional ledger partnerships
- **Latin America and other regions**: entry based on partnership maturity, regional demand, and node distribution

QCC's global path is not "simultaneously occupying the world" — it is:

> **First establishing real pivots in key regions, then forming a multi-region coordinated network through those pivots.**

---

## 6.7　QCC Native Coin and Long-Term Operating Order

QCC's macroeconomic logic must ultimately return to the native coin, rules, and long-term order.

The QCC native coin is not a speculative symbol independent of the system — it is:

- The foundational cost unit for all on-chain transactions
- The unified value foundation for rule execution and resource consumption
- An important carrier for staking, governance, execution rights, and long-term order

As established in the preceding chapter, the QCC native coin uses a fixed total supply, unified precision, and an integer ledger model. From a macroeconomic order perspective, it further bears a deeper role:

**First, unified transaction cost.** Since all on-chain actions in QCC can be unified as transactions, whether transfers, rule invocations, data on-chain, market participation, or future automated actions — all must ultimately bear their unified cost through the QCC native coin.

**Second, unified rule order.** QCC's rules are not verbal rules — they are on-chain rules. For rules to be published, invoked, and governed, they must form an institutional relationship with the native coin.

**Third, unified incentives and constraints.** Developer rewards, storage rewards, node participation, governance staking, and execution right boundaries will all be built around the QCC native coin in the future. The native coin therefore represents not only value, but also responsibility, qualification, and the cost of order.

**Fourth, unified long-term operating logic.** QCC's goal is not to be sustained indefinitely by a single company — it is to progressively form a system in which:

- The protocol itself can run
- Rules themselves can constrain
- Incentives themselves can be distributed
- Nodes themselves can participate
- Developers themselves can receive returns
- Governance itself can evolve

From this perspective, the QCC native coin is not an auxiliary financial object outside the system — it is:

> **The value core of QCC's entire long-term operating order.**

It unifies transactions, rules, incentives, constraints, governance, and global expansion within the same value boundary.

---

## 6.8　Long-Term Goal: From Protocol Viability to Global Trusted Infrastructure

QCC's long-term goal is not merely to become a chain — nor merely to be one project in one sector.

QCC's longer-term vision is to become:

> **The underlying operating system of the global trusted byte network.**

The "global trusted infrastructure" referred to here is not an empty slogan — it means QCC aspires to simultaneously host:

- Global financial and payment coordination
- Stablecoin and rule asset networks
- Enterprise ledgers and trusted data services
- Supply chain bills, auditing, and judicial notarization
- AI data provenance and intelligent agent collaboration
- New-generation on-chain application systems built by developers on top of QCC

This means QCC's future will not be limited to:

- Only building a mainnet
- Only building a wallet
- Only building a DEX
- Only building a token issuance platform

What it truly aims to become is a foundational protocol environment jointly accessed by different regions, institutions, developers, and asset networks.

From a governance perspective, it will evolve from single-signature founding advancement to multi-sig trusteeship, and then to constitutional governance. From an economic perspective, it will evolve from native coins and genesis distribution to a long-term order jointly formed by developers, nodes, regions, and the ecosystem. From a geographic perspective, it will evolve from a localized launch to Asian and US pivots, progressively forming a multi-region coordinated entry point network.

QCC's development trajectory therefore does not pursue short-term narratives, does not pursue phased hype, and does not pursue turning everything into marketing. What it truly pursues is:

- **Sustainably operable**
- **Sustainably extensible**
- **Sustainably deployable**
- **Sustainably accessible to the world**

If the preceding chapters answered how QCC came into being, how it operates, and how it expresses its own identity — then this chapter answers:

> **How QCC transitions from a viable protocol to infrastructure that the world truly accesses.**

---

# Closing

QCC began with finance. It ends with history.

Through a secure, post-quantum foundational implementation, I hope to build a trusted human record that spans a century — so that history can henceforth be written by anyone, preserved by anyone, and proven by anyone.
